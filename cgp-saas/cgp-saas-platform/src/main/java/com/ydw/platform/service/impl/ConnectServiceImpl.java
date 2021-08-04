package com.ydw.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.dao.ConnectMapper;
import com.ydw.platform.model.constant.Constant;
import com.ydw.platform.model.db.Connect;
import com.ydw.platform.model.enums.ConnectStatusEnum;
import com.ydw.platform.model.enums.MessageTopicEnum;
import com.ydw.platform.model.vo.ConnectMsg;
import com.ydw.platform.model.vo.ConnectVO;
import com.ydw.platform.model.vo.Msg;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.redis.RedisUtil;
import com.ydw.platform.service.IConnectService;
import com.ydw.platform.service.IResourceService;
import com.ydw.platform.service.IUserHangupService;
import com.ydw.platform.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
@Service
public class ConnectServiceImpl extends ServiceImpl<ConnectMapper, Connect> implements IConnectService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${offline.timeout}")
    private Long timeout;

    @Value("${offline.releaseTime}")
    private Long releaseTime;

    @Value("${url.paasApi}")
    private String paasUrl;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    @Lazy
    private IResourceService resourceServiceImpl;

    @Autowired
    private FeginClientService feginClientService;

    @Autowired
    private IUserHangupService userHangupService;

    @Override
    public void abnormalConnect(String connectId) {
        Double l = redisUtil.zScore(Constant.ZSET_OFFLINE, connectId);
        long v = (System.currentTimeMillis() - l.longValue()) / 1000;
        logger.info("此连接{}掉线时间戳{}，共计{}秒",connectId, l,v);
        if (l != null && l != -1 && v > timeout) {
            long endDuration = userHangupService.getHangupEndDuration(connectId);
            if (endDuration != -1l){
                logger.info("此连接【{}】正在挂机，不处理异常连接情况", connectId);
                return;
            }
            //掉线超时
            Connect connect = getById(connectId);
            if (connect.getEndTime() == null){
                //连接未结束
                ConnectVO vo = new ConnectVO();
                vo.setAccount(connect.getUserId());
                vo.setConnectId(connect.getId());
                vo.setDeviceId(connect.getDeviceId());
                vo.setAppId(connect.getAppId());
                resourceServiceImpl.release(vo);
            }
            removeOffline(connectId);
        }
    }

    @Override
    public ResultInfo getConnectInfo(String connectId) {
        Connect connect = getById(connectId);
        return ResultInfo.success(connect);
    }

    @Override
    public void offlineScan() {
        //获取ws掉线时间超过时长的
        long l = System.currentTimeMillis();
        Set<Object> offline = redisUtil.zRange(Constant.ZSET_OFFLINE, Double.MIN_VALUE, new Double(l - timeout * 1000));
        for (Object obj : offline) {
            logger.info("此连接【{}】挂机时间过长",obj);
            Connect connect = getById((String)obj);
            if (connect == null || connect.getEndTime() != null){
                removeOffline((String)obj);
                continue;
            }
            long endDuration = userHangupService.getHangupEndDuration(connect.getId());
            if (endDuration != -1l){
                logger.info("此连接【{}】正在挂机，异常连接扫描跳过", connect.getId());
                continue;
            }
            //给c端发送消息
            Msg msg = new Msg();
            ConnectMsg connectMsg = new ConnectMsg();
            connectMsg.setConnectId(connect.getId());
            connectMsg.setConnectStatus(ConnectStatusEnum.ABNORMALCONNECT.status);
            connectMsg.setDetail(timeout);
            msg.setData(JSON.toJSONString(connectMsg));
            List<String> userOnlineTokens = feginClientService.getUserOnlineTokens(connect.getUserId());
            msg.setReceivers(userOnlineTokens);
            msg.setType(MessageTopicEnum.CONNECT.getTopic());
            logger.info("message:{}",JSON.toJSONString(msg));
            feginClientService.sendMsg(msg);
            //创建几秒钟后关机流程
            HashMap<String,Object> job = new HashMap<>();
            //连接id+上次计费记录id
            job.put("jobName",obj);
            job.put("jobGroup",Constant.ABNORMAL_CONNECT_JOB);
            job.put("server","cgp-saas-platform");
            job.put("path", Constant.Url.URL_PLATFROM_ABNORMALCONNECT);
            job.put("method","get");
            HashMap<String,Object> params = new HashMap<>();
            params.put("connectId",obj);
            job.put("params", JSON.toJSONString(params));
            job.put("cron", DateUtil.dateFormatcron(LocalDateTime.now().plusSeconds(releaseTime)));
            feginClientService.addScheduleJob(job);
        }
    }

    @Override
    public void connectCallback(String connectId, String account ,int status, Object detail) {
        ConnectStatusEnum anEnum = ConnectStatusEnum.getEnum(status);
        switch (anEnum) {
            //未连接
            case UNCONNECT:
                break;
            //申请连接
            case APPLYCONNECT:
                Msg msg = new Msg();
                ConnectMsg connectMsg = new ConnectMsg();
                connectMsg.setConnectId(connectId);
                connectMsg.setConnectStatus(status);
                JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(detail));
                if(jsonObject != null){
                    resourceServiceImpl.replacePics(jsonObject, jsonObject.getString("appId"));
                    connectMsg.setDetail(jsonObject);
                }
                msg.setData(JSON.toJSONString(connectMsg));
                List<String> userOnlineTokens = feginClientService.getUserOnlineTokens(account);
                msg.setReceivers(userOnlineTokens);
                msg.setType(MessageTopicEnum.CONNECT.getTopic());
                logger.info("message:{}",JSON.toJSONString(msg));
                feginClientService.sendMsg(msg);
                if (jsonObject.getLongValue("queuedTime") >= 2 * 60){
                    delayConnectNotice(account, jsonObject.getString("appName"),connectId);
                }
                break;
            //连接中
            case CONNECTING:
                removeOffline(connectId);
                msg = new Msg();
                connectMsg = new ConnectMsg();
                connectMsg.setConnectId(connectId);
                connectMsg.setConnectStatus(status);
                jsonObject = JSON.parseObject(JSON.toJSONString(detail));
                if(jsonObject != null){
                    resourceServiceImpl.replacePics(jsonObject, jsonObject.getString("appId"));
                    connectMsg.setDetail(jsonObject);
                }
                msg.setData(JSON.toJSONString(connectMsg));
                userOnlineTokens = feginClientService.getUserOnlineTokens(account);
                msg.setReceivers(userOnlineTokens);
                msg.setType(MessageTopicEnum.CONNECT.getTopic());
                logger.info("message:{}",JSON.toJSONString(msg));
                feginClientService.sendMsg(msg);
                cancelConnectNotice(connectId);
                break;
            //退出连接
            case CONNECTOUT:
                resourceServiceImpl.saasRelease(connectId, account);
                break;
            //异常连接
            case ABNORMALCONNECT:
                //异常退出写入掉线状态缓存中
                addOffline(connectId);
                break;
            //设备控制异常
            case CONTROLRROR:
                addOffline(connectId);
        }
    }

    /**
     * 移除掉线
     * @param connectId
     */
    @Override
    public void removeOffline(String connectId){
        logger.info("此连接已经上线或者下机！connectId={}", connectId);
        redisUtil.zRemove(Constant.ZSET_OFFLINE, connectId);
    }

    /**
     * 掉线
     * @param connectId
     */
    @Override
    public void addOffline(String connectId){
        logger.info("此连接已经掉线！connectId={}", connectId);
        Double score = redisUtil.zScore(Constant.ZSET_OFFLINE, connectId);
        if (score == null || score == -1){
            redisUtil.zSet(Constant.ZSET_OFFLINE, connectId, new Double(System.currentTimeMillis()));
        }
    }

    @Override
    public void delayConnectNotice(String account, String appName, String connectId){
        HashMap<String,Object> job = new HashMap<>();
        //连接id+上次计费记录id
        job.put("jobName",connectId);
        job.put("jobGroup",Constant.QUEUE_MESSAGE_JOB);
        job.put("server","cgp-saas-platform");
        job.put("path", "/resource/queueMessageNotice");
        job.put("method","post");
        HashMap<String,Object> params = new HashMap<>();
        params.put("connectId",connectId);
        params.put("appName", appName);
        params.put("account", account);
        job.put("params", JSON.toJSONString(params));
        Date date = DateUtil.addSeconds(new Date(), 30);
        String cron = DateUtil.dateFormatcron(date);
        job.put("cron", cron);
        feginClientService.addScheduleJob(job);
    }

    @Override
    public void cancelConnectNotice(String connectId){
        feginClientService.deleteScheduleJob(connectId, Constant.QUEUE_MESSAGE_JOB);
    }
}
