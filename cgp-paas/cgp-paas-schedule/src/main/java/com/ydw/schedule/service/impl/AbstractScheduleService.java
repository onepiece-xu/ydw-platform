package com.ydw.schedule.service.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ydw.schedule.model.constant.RedisConstant;
import com.ydw.schedule.service.IScheduleService;
import com.ydw.schedule.util.RedisUtil;

public abstract class AbstractScheduleService implements IScheduleService{

    private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 * 根据设备获取连接
	 * @param deviceId
	 * @return
	 */
	public String getConnectByDevice(String deviceId){
		return (String)redisUtil.hget(RedisConstant.SCH_MAP_DEVICE_CONNECT, deviceId);
	}
	
	/**
	 * 根据连接获取设备
	 * @param connectId
	 * @return
	 */
	public String getDeviceByConnect(String connectId){
		return (String)redisUtil.hget(RedisConstant.SCH_MAP_CONNECT_DEVICE, connectId);
	}
	
	/**
	 * 锁住设备，并移除调度，不让其他线程使用
	 * @param deviceId
	 * @return
	 */
	@Override
	public boolean lockDevice(String clusterId, String deviceId){
		boolean lock = lockDevice(deviceId);
		if(lock){
			String clusterIdleKey = MessageFormat.format(RedisConstant.SCH_ZSET_CLUSTER_IDLE, clusterId);
			lock = redisUtil.zRemove(clusterIdleKey, deviceId) > 0l;
            logger.info("设备{}锁住后从idle池{}中移除{}！", deviceId, clusterId, lock);
            if (!lock){
            	unlockDevice(deviceId);
			}
		}
		return lock;
	}

	/**
	 * 锁住设备，不让其他线程使用
	 * @param deviceId
	 * @return
	 */
	@Override
	public boolean lockDevice(String deviceId){
		String deviceLock = MessageFormat.format(RedisConstant.SCH_LOCK_USE_DEVICE, deviceId);
		return redisUtil.lock(deviceLock);
	}
	
	/**
	 * 解锁设备
	 * @param deviceId
	 * @return
	 */
	@Override
	public void unlockDevice(String deviceId){
		String deviceLock = MessageFormat.format(RedisConstant.SCH_LOCK_USE_DEVICE, deviceId);
		//先锁住
		redisUtil.del(deviceLock);
	}
}
