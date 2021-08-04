package com.ydw.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.ydw.platform.dao.ClusterNetbarMapper;
import com.ydw.platform.model.constant.Constant;
import com.ydw.platform.model.db.*;
import com.ydw.platform.model.enums.ConnectStatusEnum;
import com.ydw.platform.model.enums.MessageTopicEnum;
import com.ydw.platform.model.vo.*;
import com.ydw.platform.redis.RedisUtil;
import com.ydw.platform.service.*;
import com.ydw.platform.task.ThreadPool;
import com.ydw.platform.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ResourceServiceImpl implements IResourceService{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FeginClientService feginClientService;

    @Autowired
    private IConnectService connectServiceImpl;

    @Autowired
    private IYdwChargeService ydwChargeService;

    @Value("${url.paasApi}")
    private String paasUrl;

    @Value("${url.pics}")
    private String picsUrl;

    @Autowired
    private IYdwMessageService ydwMessageService;

    @Autowired
    private IAppPicturesService appPicturesService;

    @Autowired
    private IVirtualkeyUsedService virtualkeyUsedService;

    @Autowired
    private ClusterNetbarMapper clusterNetbarMapper;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IDadagameService dadagameService;

    @Autowired
    private IPlayCardChargeService playCardChargeService;

    @Autowired
    private IOutGameMapService outGameMapService;

    @Autowired
    private IUserHangupService userHangupService;

    @Autowired
    private ThreadPool threadPool;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResultInfo apply(ApplyParameter param) {
//        List<IpBlack> list = ipBlackService.list();
//        if (list != null && !list.isEmpty()){
//            List<String> ips = list.stream().map(IpBlack::getIp).collect(Collectors.toList());
//            if(ips.contains(param.getCustomIp())){
//                return ResultInfo.fail(203,"检测到使用违规ip，请联系客服！");
//            }
//        }
        String able = ydwChargeService.chargeAble(param.getAccount());
        if(able == null || !JSON.parseObject(able).getBoolean("data")){
            return ResultInfo.fail(202,"余额不足！");
        }
        String result = HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_APPLY,
                feginClientService.buildHeaders(), param);
        logger.info("申请连接请求paas平台返回结果：{}", result);
        JSONObject jsonResult = JSON.parseObject(result);
        JSONObject jsonData = jsonResult.getJSONObject("data");
        replacePics(jsonData, param.getAppId());
        jsonResult.put("data", jsonData);
        return jsonResult.toJavaObject(ResultInfo.class);
    }

    @Override
    public ResultInfo rentalApplyAble(String account, String appId) {
        Map<String, Boolean> data = new HashMap<>();
        //对接游戏管家，去申请账号
        QueryWrapper<OutGameMap> qw = new QueryWrapper<>();
        qw.eq("self_game_id", appId);
        qw.eq("type", 1);
        qw.eq("valid", true);
        OutGameMap outGameMap = outGameMapService.getOne(qw);
        data.put("appRentalApplyAble", outGameMap != null);
        boolean rentalNumChargeAble = playCardChargeService.playCardChargeAble(account);
        data.put("userRentalApplyAble", rentalNumChargeAble);
        return ResultInfo.success(data);
    }

    @Override
    public ResultInfo rentalApply(ApplyParameter param) {
        String able = ydwChargeService.chargeAble(param.getAccount());
        if(able == null || !JSON.parseObject(able).getBoolean("data")){
            return ResultInfo.fail(202,"余额不足！");
        }
        boolean rentalNumChargeAble = playCardChargeService.playCardChargeAble(param.getAccount());
        if (!rentalNumChargeAble){
            return ResultInfo.fail("没有可用畅玩卡！");
        }
        String result = HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_APPLY,
                feginClientService.buildHeaders(), param);
        logger.info("申请连接请求paas平台返回结果：{}", result);
        JSONObject jsonResult = JSON.parseObject(result);
        //保存上一次请求
        saveApplyParams(param);
        JSONObject jsonData = jsonResult.getJSONObject("data");
        replacePics(jsonData, param.getAppId());
        jsonResult.put("data", jsonData);
        return jsonResult.toJavaObject(ResultInfo.class);
    }

    @Override
    public ResultInfo getRank(String account) {
        HashMap<String, String> params = new HashMap<>();
        params.put("account", account);
        String result = HttpUtil.doGet(paasUrl + Constant.Url.URL_PAAS_GETRANK,
                feginClientService.buildHeaders(), params);
        return JSON.parseObject(result, ResultInfo.class);
    }

    @Override
    public ResultInfo cancelConnect(PrepareParams param) {
        connectServiceImpl.cancelConnectNotice(param.getConnectId());
        delApplyParams(param.getAccount());
        String result = HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_CANCELCONNECT,
                feginClientService.buildHeaders(), param);
        return JSON.parseObject(result, ResultInfo.class);
    }

    @Override
    public ResultInfo prepare(PrepareParams param) {
        connectServiceImpl.cancelConnectNotice(param.getConnectId());
        ApplyParameter applyParams = getApplyParams(param.getAccount());
        if (applyParams != null && applyParams.getRentalType() == 1 && delApplyParams(param.getAccount())){
            //对接游戏管家，去申请账号
            QueryWrapper<OutGameMap> qw = new QueryWrapper<>();
            qw.eq("self_game_id", param.getAppId());
            qw.eq("type", 1);
            qw.eq("valid", true);
            OutGameMap outGameMap = outGameMapService.getOne(qw);
            if (outGameMap == null){
                logger.error("此游戏{}没有对接游戏管家的游戏账号！", param.getAppId());
                cancelConnect(param);
                return ResultInfo.fail("此游戏暂时没有无法租号！");
            }
            ResultInfo resultInfo = dadagameService.borrowNum(param.getAccount(), outGameMap.getOutGameId());
            if (resultInfo.getCode() != 200){
                cancelConnect(param);
                return ResultInfo.fail("此游戏暂时无号可租！");
            }else{
                param.setRentalParams(resultInfo.getData());
            }
        }
        String result = HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_PREPARE,
                feginClientService.buildHeaders(), param);
        logger.info("请求paas平台返回结果：{}", result);
        JSONObject jsonResult = JSON.parseObject(result);
        if(jsonResult.getIntValue("code") == 200){
            JSONObject jsonData = jsonResult.getJSONObject("data");
            ResultInfo defaultVirtualkeyConfig = virtualkeyUsedService.getDefaultVirtualkeyConfig(param.getAccount(), param.getAppId());
            jsonData.put("virtualkeyConfig", defaultVirtualkeyConfig.getData());
            if(jsonData.getBoolean("newConnect")){
                DeviceConnectInfo info = jsonData.toJavaObject(DeviceConnectInfo.class);
                Connect connect = connectServiceImpl.getById(info.getConnectId());
                if(connect == null){
                    //创建连接
                    connect = new Connect();
                    connect.setId(info.getConnectId());
                    connect.setAbnormalTime(null);
                    connect.setAppId(param.getAppId());
                    connect.setBeginTime(LocalDateTime.now());
                    connect.setClient(param.getClient());
                    connect.setUserId(param.getAccount());
                    connect.setDeviceId(info.getDeviceId());
                    connect.setDeviceName(info.getDeviceName());
                    connect.setClusterId(info.getClusterId());
                    connect.setClusterName(info.getClusterName());
                    connect.setFromIp(param.getCustomIp());
                    connect.setType(param.getType());
                    if (param.getRentalParams() != null){
                        connect.setRentalParams(param.getRentalParams().toString());
                        //租号计费
                        playCardChargeService.startPlayCardCharge(connect.getId(),param.getAccount());
                        JSONObject jsonObject = JSON.parseObject(param.getRentalParams().toString());
                        dadagameService.saveStartGameNoToConnectId(jsonObject.getString("startGameNo"), connect.getId());
                    }
                    connectServiceImpl.save(connect);
                    //计费
                    ydwChargeService.startCharge(connect.getId(),param.getAccount());
                }
            }
            replacePics(jsonData, param.getAppId());
            jsonResult.put("data", jsonData);
        }else{
            if (param.getRentalParams() != null){
                JSONObject parse = JSON.parseObject(param.getRentalParams().toString());
                //租号计费
                dadagameService.returnNum(parse.getString("startGameNo"));
            }
        }
        return jsonResult.toJavaObject(ResultInfo.class);
    }

    @Override
    public ResultInfo applyByNetbar(ApplyParameter param) {
        QueryWrapper<ClusterNetbar> qw = new QueryWrapper<>();
        qw.eq("netbar_id", param.getNetbarId());
        List<ClusterNetbar> netbarList = clusterNetbarMapper.selectList(qw);
        param.setClusterIds(netbarList.stream().map(ClusterNetbar::getClusterId).collect(Collectors.toList()));
        return apply(param);
    }

    @Override
    public ResultInfo queueOut(String account) {
        delApplyParams(account);
        HashMap<String, String> params = new HashMap<>();
        params.put("account", account);
        String result = HttpUtil.doGet(paasUrl + Constant.Url.URL_PAAS_QUEUEOUT,
                feginClientService.buildHeaders(), params);
        return JSON.parseObject(result, ResultInfo.class);
    }

    @Override
    public ResultInfo reconnect(ConnectVO vo) {
        logger.info("此连接重新重连{}",JSON.toJSONString(vo));
        String result = HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_RECONNECT,
                feginClientService.buildHeaders(), vo);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        if (resultInfo.getCode() != 200){
            release(vo);
            return ResultInfo.fail();
        }else{
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(resultInfo.getData()));
            replacePics(jsonObject, vo.getAppId());
            ResultInfo defaultVirtualkeyConfig = virtualkeyUsedService.getDefaultVirtualkeyConfig(vo.getAccount(), vo.getAppId());
            jsonObject.put("virtualkeyConfig",defaultVirtualkeyConfig.getData());
            resultInfo.setData(jsonObject);
        }
        return resultInfo;
    }

    @Override
    public ResultInfo release(ConnectVO vo) {
        saasRelease(vo.getConnectId(), vo.getAccount());
        HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_RELEASE,
                feginClientService.buildHeaders() , vo);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo release(String connectId) {
        Connect c = connectServiceImpl.getById(connectId);
        ConnectVO vo = new ConnectVO();
        vo.setAccount(c.getUserId());
        vo.setAppId(c.getAppId());
        vo.setConnectId(connectId);
        vo.setDeviceId(c.getDeviceId());
        return release(vo);
    }

    @Override
    public ResultInfo saasRelease(String connectId, String account) {
        boolean locked = redisUtil.lock(MessageFormat.format(Constant.STR_CLOSING, connectId), connectId, 5L);
        if (locked){
            connectServiceImpl.removeOffline(connectId);
            Connect c = connectServiceImpl.getById(connectId);
            if (c == null || c.getEndTime() != null){
                logger.info("saas端已经释放此连接{}资源",connectId);
                return ResultInfo.success();
            }else{
                LocalDateTime now = LocalDateTime.now();
                c.setEndTime(now);
                c.setTotalTime(Duration.between(c.getBeginTime(), c.getEndTime()).getSeconds());
                connectServiceImpl.updateById(c);
            }
            logger.info("结束计费！connectId={}",connectId);
            //结束计费
            ydwChargeService.endCharge(connectId);
            //结束挂机
            userHangupService.endHangup(connectId);
            //释放租号
            if (c.getRentalParams() != null){
                //停止畅玩卡的计量
                playCardChargeService.endPlayCardCharge(connectId, account);
                JSONObject parse = JSON.parseObject(c.getRentalParams());
                dadagameService.returnNum(parse.getString("startGameNo"));
            }
            redisUtil.del(MessageFormat.format(Constant.STR_CLOSING,connectId));
            threadPool.submit(() -> {
                //推送关闭游戏给c端用户
                logger.info("推送关闭消息给c端用户！connectId={}",connectId);
                Msg msg = new Msg();
                ConnectMsg connect = new ConnectMsg();
                connect.setConnectId(connectId);
                connect.setConnectStatus(ConnectStatusEnum.CONNECTOUT.status);
                msg.setData(JSON.toJSONString(connect));
                List<String> userOnlineTokens = feginClientService.getUserOnlineTokens(account);
                msg.setReceivers(userOnlineTokens);
                msg.setType(MessageTopicEnum.CONNECT.getTopic());
                msg.setDealType(1);
                ydwMessageService.sendMsg(msg);
            });
        }
        return ResultInfo.success();
    }

    /**
     * 历史遗留问题，saas的状态与paas不对应，但是没关系，客户端能使用
     * 200：已分配资源，且用户已确认，
     * 201：排队，
     * 202：已分配资源，但用户未确认，
     * 203：挂机中，
     * 204：用户没有正在进行的资源
     * 500：失败
     * @param account
     * @return
     */
    @Override
    public ResultInfo getUserConnectStatus(String account) {
        Map<String, String> params = new HashMap<>();
        params.put("account", account);
        String result = HttpUtil.doGet(paasUrl + Constant.Url.URL_PAAS_USERCONNECTSTATUS,
                feginClientService.buildHeaders(), params);
        logger.info("获取用户状态结果{}",result);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        if (resultInfo.getData() != null){
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(resultInfo.getData()));
            String appId = jsonObject.getString("appId");
            if (StringUtils.isNotBlank(appId)){
                replacePics(jsonObject, appId);
            }
            if (resultInfo.getCode() != 201){
                //查询默认配置
                ResultInfo defaultVirtualkeyConfig = virtualkeyUsedService.getDefaultVirtualkeyConfig(account, appId);
                jsonObject.put("virtualkeyConfig",defaultVirtualkeyConfig.getData());
                //查询是否在挂机
                long endDuration = userHangupService.getHangupEndDuration(jsonObject.getString("connectId"));
                if (endDuration != -1l){
                    //有挂机显示挂机
                    resultInfo.setCode(203);
                    jsonObject.put("hangupEndTime",endDuration);
                }else if (resultInfo.getCode() == 203){
                    resultInfo.setCode(200);
                }
            }
            resultInfo.setData(jsonObject);
        }else{
            resultInfo.setCode(204);
        }
        return resultInfo;
    }

    @Override
    public void replacePics(JSONObject jsonObject, String appId){
        if (StringUtils.isBlank(appId) || jsonObject == null){
            return;
        }
        QueryWrapper<AppPictures> qw = new QueryWrapper<>();
        qw.eq("app_id", appId);
        qw.eq("valid", true);
        AppPictures appPictures = appPicturesService.getOne(qw);
        if (appPictures != null){
            logger.info("开始替换游戏图片信息:{}", jsonObject);
            jsonObject.put("bigPic", appPictures.getBigPic());
            jsonObject.put("midPic", appPictures.getMidPic());
            jsonObject.put("smallPic", appPictures.getSmallPic());
            jsonObject.put("logoPic", appPictures.getLogoPic());
            logger.info("替换游戏图片信息完成:{}", jsonObject);
        }
    }

    @Override
    public ResultInfo hangup(String account, String connectId, int hangupDuration) {
        if (hangupDuration < 10){
            return ResultInfo.fail("挂机时间过短！");
        }
        int userOneDaySubHangupDuration = userHangupService.getUserOneDaySubHangupDuration(account);
        if (userOneDaySubHangupDuration < hangupDuration){
            return ResultInfo.fail(500,"每日限制的剩余挂机时长为"+userOneDaySubHangupDuration+"分钟！", userOneDaySubHangupDuration);
        }
        userHangupService.startHangup(account, connectId, hangupDuration);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo cancelHangup(String connectId) {
        userHangupService.endHangup(connectId);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo hangupEnd(String connectId, Date hangupEndTime) {
        //TODO 判断连接挂机时间是否到期
        //判断连接是否已经掉线
        Double score = redisUtil.zScore(Constant.ZSET_OFFLINE, connectId);
        //如果掉线：则关机
        if (!(score == null || score == -1)){
            Connect connect = connectServiceImpl.getById(connectId);
            ConnectVO connectVO = new ConnectVO();
            connectVO.setConnectId(connectId);
            connectVO.setAccount(connect.getUserId());
            connectVO.setDeviceId(connect.getDeviceId());
            connectVO.setAppId(connect.getAppId());
            release(connectVO);
        }
        userHangupService.endHangup(connectId);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo releaseUserRes(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("account", userId);
        String result = HttpUtil.doGet(paasUrl + Constant.Url.URL_PAAS_USERCONNECTSTATUS,
                feginClientService.buildHeaders(), params);
        logger.info("获取用户状态结果{}",result);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
        if (resultInfo.getData() != null){
            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(resultInfo.getData()));
            if (resultInfo.getCode() == 200){
                ConnectVO vo = new ConnectVO();
                vo.setAccount(userId);
                vo.setAppId(jsonObject.getString("appId"));
                vo.setDeviceId(jsonObject.getString("deviceId"));
                vo.setConnectId(jsonObject.getString("connectId"));
                release(vo);
            }
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo openApp(String account, String connectId, String deviceId, String appId) {
        Connect connect = connectServiceImpl.getById(connectId);
        JSONObject json = null;
        if (connect.getRentalParams() != null){
            json = JSON.parseObject(connect.getRentalParams());
        }
        OpenAndCloseAppParameter openAppParameter = new OpenAndCloseAppParameter(deviceId, account, appId, connectId, json);
        String s = HttpUtil.doJsonPost(paasUrl + Constant.Url.URL_PAAS_OPENAPP,
                feginClientService.buildHeaders(), openAppParameter);
        return JSONObject.parseObject(s, ResultInfo.class);
    }

    @Override
    public ResultInfo getHangupEndTime(String connectId){
        ResultInfo resultInfo = ResultInfo.success();
        long endDuration = userHangupService.getHangupEndDuration(connectId);
        if (endDuration != -1l){
            resultInfo.setData(endDuration);
        }
        return resultInfo;
    }

    @Override
    public ResultInfo queueMessageNotice(String account, String appName, String connectId) {
        try {
            logger.info("开始发送短信通知用户{}连接{}此游戏{}", account, appName, connectId);
            String[] params = {appName};
            UserInfo userInfo = userInfoService.getById(account);
            SmsSingleSender ssender = new SmsSingleSender(1400349137, "125873d60b2cee73ca4161ba2fb74edb");
            // 签名参数未提供或者为空时，会使用默认签名发送短信
            SmsSingleSenderResult result = ssender.sendWithParam("86", userInfo.getMobileNumber(), 1040014, params, "易点玩云游戏", "", "");
            logger.info("给用户{}发送短信返回结果！{}", account, result.toString());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return ResultInfo.success();
    }

    private void saveApplyParams(ApplyParameter parameter){
        if (getApplyParams(parameter.getAccount()) == null){
            String key = MessageFormat.format(Constant.STR_APPLYPARAMETER, parameter.getAccount(), 6 * 60 * 60);
            redisUtil.set(key, parameter);
        }
    }

    private ApplyParameter getApplyParams(String account){
        String key = MessageFormat.format(Constant.STR_APPLYPARAMETER, account);
        return (ApplyParameter)redisUtil.get(key);
    }

    private boolean delApplyParams(String account){
        String key = MessageFormat.format(Constant.STR_APPLYPARAMETER, account);
        return redisUtil.del(key);
    }
}
