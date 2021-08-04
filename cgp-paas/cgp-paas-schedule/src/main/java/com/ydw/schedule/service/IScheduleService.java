package com.ydw.schedule.service;

import com.ydw.schedule.model.vo.ConnectVO;

import java.util.List;
import java.util.Set;

/**
 * 调度接口
 * @author xulh
 *
 */
public interface IScheduleService {

    /**
     * 申请设备
     * @param clusterIds
     * @param appId
     * @return
     */
    ConnectVO applyConnect(List<String> clusterIds, String appId, String baseId);

    /**
     * 申请设备
     * @param clusterId
     * @param appId
     * @param baseId
     * @param start
     * @param end
     * @return
     */
	ConnectVO applyConnect(String clusterId, String appId, String baseId, long start, long end);
	
	/**
	 * 归还设备
	 * @param deviceId
	 * @param connectId
	 * @return
	 */
	boolean releaseConnect(String deviceId, String connectId);

	/**
	 * 将设备移除调度
	 * @param clusterId
	 * @param deviceId
	 */
	boolean detachSchedule(String clusterId ,String deviceId);

	/**
	 * 将设备加入调度
	 * @param clusterId
	 * @param deviceId
	 */
	boolean attachSchedule(String clusterId ,String deviceId);

	/**
	 * 添加有此游戏的设备
	 * @param clusterId
	 * @param appId
	 * @param deviceId
	 */
	void addAppToDevice(String clusterId, String appId, String deviceId);

	/**
	 * 移除此游戏下的设备
	 * @param clusterId
	 * @param appId
	 * @param deviceId
	 */
	void delAppFromDevice(String clusterId, String appId, String deviceId);

	/**
	 * 锁住设备，不让其他线程使用
	 * @param clusterId
	 * @param deviceId
	 * @return
	 */
	boolean lockDevice(String clusterId, String deviceId);

	/**
	 * 锁住设备，不让其他线程使用
	 * @param deviceId
	 * @return
	 */
	boolean lockDevice(String deviceId);
	
	/**
	 * 解锁设备
	 * @param deviceId
	 * @return
	 */
	void unlockDevice(String deviceId);

	/**
	 * 获取集群中可以使用设备
	 * @param clusterId
	 * @return
	 */
	Set<Object> getIdleDevices(String clusterId);

    /**
     * 判断集群中是否有可用设备
     * @param clusterId
     * @return
     */
	boolean hasIdleDevices(String clusterId);

    /**
     * 获取集群中可用设备数量
     * @param clusterId
     * @return
     */
	long idleDeviceCount(String clusterId);
}
