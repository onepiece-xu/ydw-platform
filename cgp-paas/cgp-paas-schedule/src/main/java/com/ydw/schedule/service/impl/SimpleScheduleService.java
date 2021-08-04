package com.ydw.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydw.schedule.model.constant.RedisConstant;
import com.ydw.schedule.model.db.TbDevices;
import com.ydw.schedule.model.enums.DeviceStatusEnum;
import com.ydw.schedule.model.vo.ConnectVO;
import com.ydw.schedule.model.vo.RedisZSetVO;
import com.ydw.schedule.service.IDeviceService;
import com.ydw.schedule.service.ITbDeviceAppsService;
import com.ydw.schedule.util.RedisUtil;
import com.ydw.schedule.util.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

/**
 * 简单调度器
 * 自动选区： 首先按照延迟大小顺序排列获取到空闲的机器，区排序优先级高的空闲机器中有游戏可用则直接给予
 * 手动选区： 获取指定区的空闲机器
 * 
 * @author xulh
 *
 */
@Service
public class SimpleScheduleService extends AbstractScheduleService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private IDeviceService devicesServiceImpl;

	@Autowired
	private ITbDeviceAppsService tbDeviceAppsServiceImpl;

    /**
     * 根据选区情况和appId获取设备
     * @param clusterIds
     * @param appId
     * @param baseId
     * @return
     */
    @Override
    public ConnectVO applyConnect(List<String> clusterIds, String appId, String baseId) {
        return applyConnect(clusterIds, appId, baseId, 0L, -1L);
    }


    public ConnectVO applyConnect(List<String> clusterIds, String appId, String baseId, long start, long end) {
        ConnectVO connectVO = null;
        for (String clusterId : clusterIds) {
            if (StringUtils.isNotBlank(clusterId)){
                connectVO = applyConnect(clusterId, appId, baseId, start, end);
                if (connectVO != null){
                    break;
                }
            }
        }
        return connectVO;
    }

    /**
	 * 根据选区情况和appId获取设备
	 * @param clusterId
	 * @param appId
	 */
    @Override
	public ConnectVO applyConnect(String clusterId, String appId, String baseId, long start, long end) {
		logger.info("开始在【{}】节点中查找app【{}】", clusterId, appId);
		//先做同步动作
		sychDevicesToRedis(clusterId, appId);
		ConnectVO vo = findDevice(clusterId, appId, baseId, start, end);
		if (vo != null) {
			logger.info("最终在【{}】节点中查找了app【{}】", clusterId, appId);
		}else{
			logger.info("在【{}】节点中没有找到app【{}】", clusterId, appId);
		}
		return vo;
	}

	/**
	 * 根据边缘节点和游戏查找设备
	 * 并将此设备锁住
	 * @param clusterId 边缘节点id
	 * @param appId 游戏id
	 */
	private ConnectVO findDevice(String clusterId, String appId, String baseId, long start, long end) {
		ConnectVO vo = null;
		String deviceId = null;
		//先从redis中判断是否有数据
		String clusterIdleKey = MessageFormat.format(RedisConstant.SCH_ZSET_CLUSTER_IDLE, clusterId);
		String clusterAppKey = MessageFormat.format(RedisConstant.SCH_SET_CLUSTER_APP, clusterId, appId);
		if(redisUtil.hasKey(clusterIdleKey) && redisUtil.hasKey(clusterAppKey)){
            //空闲的设备
            Set<Object> idleDevices = redisUtil.zRangeObject(clusterIdleKey, start, end, true);
			//已安装此应用的设备
            Set<Object> appInstalledDevices = redisUtil.sGet(clusterAppKey);
            for (Object idleDevice : idleDevices) {
                if (appInstalledDevices.contains(idleDevice)){
                    deviceId = (String) idleDevice;
                    TbDevices tbDevices = devicesServiceImpl.getById(deviceId);
                    if(StringUtils.isBlank(baseId) || baseId.equals(tbDevices.getBaseId())){
                        if(super.lockDevice(clusterId, deviceId)){
                            super.unlockDevice(deviceId);
                            break;
                        }
                    }
                }
                deviceId = null;
            }
		}
		//获取到设备以后，修改其状态并从redis可用队列中剔除
		if(deviceId != null){
			vo = new ConnectVO(SequenceGenerator.sequence(),deviceId,appId);
			Map<Object,Object> map = new HashMap<>();
			map.put(vo.getConnectId(), vo.getDeviceId());
			redisUtil.hmset(RedisConstant.SCH_MAP_CONNECT_DEVICE, map);
			map = new HashMap<>();
			map.put(vo.getDeviceId(), vo.getConnectId());
			redisUtil.hmset(RedisConstant.SCH_MAP_DEVICE_CONNECT, map);
		}
		return vo;
	}
	
	/**
	 * 同步设备到redis中
	 * @param clusterId
	 * @param appId
	 */
	private void sychDevicesToRedis(String clusterId, String appId){
		//先从redis中判断是否有数据
		String nodeKey = MessageFormat.format(RedisConstant.SCH_LOCK_ADD_CLUSTER_UNUSED_DEVICE, clusterId);
		String clusterIdleKey = MessageFormat.format(RedisConstant.SCH_ZSET_CLUSTER_IDLE, clusterId);
		String clusterAppKey = MessageFormat.format(RedisConstant.SCH_SET_CLUSTER_APP, clusterId, appId);
		if(redisUtil.lock(nodeKey, clusterId, false)){
			if(!redisUtil.hasKey(clusterIdleKey)){
				QueryWrapper<TbDevices> qw = new QueryWrapper<>();
				qw.eq("cluster_id", clusterId);
				qw.eq("status", DeviceStatusEnum.IDLE.getStatus());
				qw.eq("sch_status", true);
				qw.eq("valid", true);
				List<TbDevices> list = devicesServiceImpl.list(qw);
				if(list != null && !list.isEmpty()){
					Set<TypedTuple<Object>> vos = new HashSet<>();
					for(TbDevices t : list){
						vos.add(new RedisZSetVO<>(t.getId(), new Double(System.currentTimeMillis())));
					}
					redisUtil.zSet(clusterIdleKey, vos);
					logger.info("初始化idle状态的设备到redis中，此次同步【{}】节点的信息",clusterId);
				}
			}
		}
		if(!redisUtil.hasKey(clusterAppKey)){
			List<String> list = tbDeviceAppsServiceImpl.getDeviceByCluAndApp(clusterId, appId);
			if(list != null && !list.isEmpty()){
				redisUtil.sSet(clusterAppKey, list.toArray());
				logger.info("初始化app的节点设备到redis中，此次同步【{}】节点的app【{}】的信息",clusterId, appId);
			}
		}
	}

	@Override
	public boolean detachSchedule(String clusterId , String deviceId) {
		boolean flag = false;
		//判断这个设备是否已经有连接，有连接的不做处理
		String connectId = super.getConnectByDevice(deviceId);
		if(StringUtils.isBlank(connectId)){
			String deviceLock = MessageFormat.format(RedisConstant.SCH_LOCK_USE_DEVICE, deviceId);
			//移除时先锁住
			if(redisUtil.lock(deviceLock, deviceId)){
				try {
					//从可用池中移除
					redisUtil.zRemove(MessageFormat.format(RedisConstant.SCH_ZSET_CLUSTER_IDLE, clusterId), deviceId);
					logger.info("设备{}从集群的idle池{}中移除！", deviceId, clusterId);
					flag = true;
				} finally {
					redisUtil.unlock(deviceLock, deviceId);
				}
			}
		}
		return flag;
	}

	@Override
	public boolean attachSchedule(String clusterId , String deviceId) {
		boolean flag = false;
		//判断这个设备是否已经有连接，有连接的不做处理
		String connectId = super.getConnectByDevice(deviceId);
		if(StringUtils.isBlank(connectId)){
			String deviceLock = MessageFormat.format(RedisConstant.SCH_LOCK_USE_DEVICE, deviceId);
			//添加时先锁住
			if(redisUtil.lock(deviceLock, deviceId)){
				try {
					//添加到可用池中
					String key = MessageFormat.format(RedisConstant.SCH_ZSET_CLUSTER_IDLE, clusterId);
					logger.info("设备{}状态准备好后开始加入集群{}中key:{}！", deviceId, clusterId, key);
					flag = redisUtil.zSet(key, deviceId, System.currentTimeMillis());
					if (flag){
						logger.info("设备{}状态准备好后加入集群{}中成功！", deviceId, clusterId);
					}else{
						logger.error("设备{}状态准备好后加入集群{}中失败！", deviceId, clusterId);
					}
				} finally {
					redisUtil.unlock(deviceLock,deviceId);
				}
				
			}
		}
		return flag;
	}

	@Override
	public void addAppToDevice(String clusterId, String appId, String deviceId) {
		String clusterAppKey = MessageFormat.format(RedisConstant.SCH_SET_CLUSTER_APP, clusterId, appId);
		redisUtil.sSet(clusterAppKey, deviceId);
	}

	@Override
	public void delAppFromDevice(String clusterId, String appId, String deviceId) {
		String clusterAppKey = MessageFormat.format(RedisConstant.SCH_SET_CLUSTER_APP, clusterId, appId);
		redisUtil.setRemove(clusterAppKey, deviceId);
	}

	@Override
	public boolean releaseConnect(String deviceId, String connectId) {
		if(StringUtils.isNotBlank(super.getConnectByDevice(deviceId)) &&
				StringUtils.isNotBlank(super.getDeviceByConnect(connectId))){
			//删除连接
			redisUtil.hdel(RedisConstant.SCH_MAP_CONNECT_DEVICE, connectId);
			redisUtil.hdel(RedisConstant.SCH_MAP_DEVICE_CONNECT, deviceId);
			//解锁
			redisUtil.del(MessageFormat.format(RedisConstant.SCH_LOCK_USE_DEVICE, deviceId));
			return true;
		}
		return false;
	}

    @Override
    public Set<Object> getIdleDevices(String clusterId) {
        String key = MessageFormat.format(RedisConstant.SCH_ZSET_CLUSTER_IDLE, clusterId);
        return redisUtil.zRangeObject(key, 0, Long.MAX_VALUE, true);
    }

    @Override
    public boolean hasIdleDevices(String clusterId) {
        String key = MessageFormat.format(RedisConstant.SCH_ZSET_CLUSTER_IDLE, clusterId);
        return redisUtil.hasKey(key);
    }

    @Override
    public long idleDeviceCount(String clusterId) {
        String key = MessageFormat.format(RedisConstant.SCH_ZSET_CLUSTER_IDLE, clusterId);
        return redisUtil.zCard(key);
    }
}
