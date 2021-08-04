package com.ydw.schedule.service.impl;

import com.alibaba.fastjson.JSON;
import com.ydw.schedule.model.constant.RedisConstant;
import com.ydw.schedule.model.db.TbDevices;
import com.ydw.schedule.model.enums.ApplyStatusEnum;
import com.ydw.schedule.model.vo.*;
import com.ydw.schedule.service.IConnectService;
import com.ydw.schedule.service.IDeviceService;
import com.ydw.schedule.service.IQueueService;
import com.ydw.schedule.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ConnectServiceImpl implements IConnectService{
	
	Logger logger  = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private AbstractScheduleService scheduleServiceImpl;

	@Autowired
	private IQueueService queueServiceImpl;
	
	@Autowired
	private IDeviceService devicesServiceImpl;

	@Autowired
	private RedisUtil redisUtil;

	/**
	 * 申请连接
	 */
	@Override
	public ResultInfo applyConnect(ApplyConnectVO vo){
		String account = vo.getAccount();
		String appId = vo.getAppId();
		String baseId = vo.getBaseId();
		List<String> clusterIds = vo.getClusterIds();
        ApplyResultVO applyResultVO = null;
		// 是否已有分配资源
		ConnectVO connectVO = getConnectByUser(account);
		if (connectVO != null) {
			applyResultVO = new ApplyResultVO(connectVO);
			applyResultVO.setStatus(ApplyStatusEnum.GOT.getStatus());
			logger.warn("用户{}已有连接{}，请重连！", account, JSON.toJSONString(connectVO));
		}
		//是否之前已经排队过
        QueueVO queueVO = queueServiceImpl.getGlobalRank(account);
		if (queueVO != null){
		    logger.info("用户{}此前已排队过，等待通知！", account);
            applyResultVO = new ApplyResultVO(queueVO);
        }
		//去空闲集群拿设备
		if (applyResultVO == null){
		    logger.info("用户{}开始在空闲的集群{}中申请游戏{}", account, clusterIds, appId);
            List<String> list = queueServiceImpl.ableApplyClusters(clusterIds, appId);
            if(!list.isEmpty()){
                applyResultVO = applyFromAllIdle(account, list, appId, baseId);
            }
        }
        //去排队集群捡漏
		if (applyResultVO == null){
            logger.info("用户{}开始在排队的集群{}中申请游戏{}", account, clusterIds, appId);
            applyResultVO = applyFromSubIdle(account, clusterIds, appId, baseId);
        }
        if (applyResultVO == null){
            if (vo.getQueue()){
                logger.info("用户{}开始在排队", account);
                queueServiceImpl.enqueue(account, clusterIds, appId, baseId);
                queueVO = queueServiceImpl.getGlobalRank(account);
                logger.info("用户{}进入全局排队{}名", account, queueVO.getRank());
                applyResultVO = new ApplyResultVO(queueVO);
            }else{
                logger.info("用户{}选择不排队，直接返回", account);
                return new ResultInfo(202, "暂时无设备可用！", null);
            }
        }
		return ResultInfo.success(applyResultVO);
	}

    /**
     * 申请所有设备
     * @param account
     * @param clusterIds
     * @param appId
     * @param baseId
     * @return
     */
	@Override
	public ApplyResultVO applyFromAllIdle(String account, List<String> clusterIds, String appId, String baseId){
        ApplyResultVO applyResultVO = null;
        for (String clusterId : clusterIds) {
            applyResultVO = applyConnect(account, clusterId, appId, baseId, 0L, -1L);
            if (applyResultVO != null){
                break;
            }
        }
        return applyResultVO;
	}

    /**
     * 申请幸存区的设备
     * @param account
     * @param clusterIds
     * @param appId
     * @param baseId
     * @return
     */
	@Override
	public ApplyResultVO applyFromSubIdle(String account, List<String> clusterIds, String appId, String baseId){
        ApplyResultVO applyResultVO = null;
        for (String clusterId : clusterIds) {
            long survivorPoint = queueServiceImpl.getSurvivorPoint(clusterId);
            if (survivorPoint == -1L || survivorPoint < 0L){
                continue;
            }
            applyResultVO = applyConnect(account, clusterId, appId, baseId, 0L, survivorPoint);
            if (applyResultVO != null){
                queueServiceImpl.decrSurvivorPoint(clusterId);
                break;
            }
        }
        return applyResultVO;
	}

    private ApplyResultVO applyConnect(String account, String clusterId, String appId, String baseId, long start, long end){
        ApplyResultVO applyResultVO = null;
        ConnectVO connectVO = getConnectByUser(account);
        if (connectVO == null) {
            String key = MessageFormat.format(RedisConstant.QUE_LOCK_ACCOUNT_APPLY, account);
            boolean lock = redisUtil.lock(key, account);
            if (lock){
                connectVO = scheduleServiceImpl.applyConnect(clusterId, appId, baseId, start, end);
                if (connectVO == null){
                    logger.warn("用户{}此次申请并未申请到设备！", account);
                }else{
                    connectVO.setAccount(account);
                    connectVO.setConnectTime(System.currentTimeMillis());
                    //用户和连接绑定
                    redisUtil.set(MessageFormat.format(RedisConstant.QUE_STR_ACCOUNT_CONNECT, account), connectVO);
                    redisUtil.set(MessageFormat.format(RedisConstant.QUE_STR_CONNECT_ACCOUNT, connectVO.getConnectId()), account);
                    logger.info("用户{}申请到连接{}！", account, JSON.toJSONString(connectVO));
                    applyResultVO = new ApplyResultVO(connectVO);
                }
                redisUtil.unlock(key, account);
            }else{
                logger.warn("用户{}已经在申请中！", account);
            }
        }else{
            logger.warn("用户{}已有连接{}，请重连！", account, JSON.toJSONString(connectVO));
            applyResultVO = new ApplyResultVO(connectVO);
            applyResultVO.setStatus(ApplyStatusEnum.GOT.getStatus());
        }
        return applyResultVO;
    }



	/**
	 * 释放连接
	 */
	@Override
	public ResultInfo releaseConnect(ConnectVO vo) {
		//归还设备移除调度
		TbDevices devices = devicesServiceImpl.getById(vo.getDeviceId());
		if(devices == null){
			logger.error("下机操作时查询不到此设备，设备id-{}", vo.getDeviceId());
			return ResultInfo.fail("无此设备id-" + vo.getDeviceId());
		}
		Boolean revokeDevice = scheduleServiceImpl.releaseConnect(vo.getDeviceId(), vo.getConnectId());
		if(revokeDevice){
			//解除用户与连接的关系
			String account = (String)redisUtil.get(MessageFormat.format(RedisConstant.QUE_STR_CONNECT_ACCOUNT, vo.getConnectId()));
			redisUtil.del(MessageFormat.format(RedisConstant.QUE_STR_CONNECT_ACCOUNT, vo.getConnectId()));
			redisUtil.del(MessageFormat.format(RedisConstant.QUE_STR_ACCOUNT_CONNECT, account));
			return ResultInfo.success();
		}else{
			logger.error("下机操作时查询不到此连接，连接id-{}", vo.getConnectId());
			return ResultInfo.fail("无此连接id-" + vo.getConnectId());
		}
	}
	
	@Override
	public ResultInfo getConnectByDevice(String deviceId) {
		String connectId = scheduleServiceImpl.getConnectByDevice(deviceId);
		if(StringUtils.isBlank(connectId)){
			logger.error("获取设备连接时获取不到连接，设备id-{}", deviceId);
			return ResultInfo.fail(MessageFormat.format("此设备{0}无连接",deviceId));
		}else{
			String account = (String)redisUtil.get(MessageFormat.format(RedisConstant.QUE_STR_CONNECT_ACCOUNT, connectId));
			Map<String ,String> map = new HashMap<>();
			map.put("account", account);
			map.put("connectId", connectId);
			return ResultInfo.success(MessageFormat.format("此设备{0}有连接",deviceId), map);
		}
	}

	@Override
	public ResultInfo getDeviceByConnect(String connectId) {
		String deviceId = scheduleServiceImpl.getDeviceByConnect(connectId);
		if(StringUtils.isBlank(connectId)){
			logger.error("获取连接设备时获取不到设备，连接id-{}", connectId);
			return ResultInfo.fail(MessageFormat.format("此连接{0}无设备",connectId));
		}else{
			return ResultInfo.success(MessageFormat.format("此连接{0}有设备",connectId), deviceId);
		}
	}

	@Override
	public ResultInfo getUserConnectStatus(String account) {
		ConnectVO connectDevice = getConnectByUser(account);
		if(connectDevice != null){
			ApplyResultVO applyResultVO = new ApplyResultVO(connectDevice);
			applyResultVO.setStatus(ApplyStatusEnum.GOT.getStatus());
			return ResultInfo.success(applyResultVO);
		}
		QueueVO queueVO = queueServiceImpl.getGlobalRank(account);
		if(queueVO != null){
			return ResultInfo.success(new ApplyResultVO(queueVO));
		}
		return ResultInfo.success();
	}
	
	/**
	 * 获取用户的设备连接
	 * @param account
	 * @return
	 */
	private ConnectVO getConnectByUser(String account) {
		return (ConnectVO) redisUtil.get(MessageFormat.format(RedisConstant.QUE_STR_ACCOUNT_CONNECT, account));
	}
}
