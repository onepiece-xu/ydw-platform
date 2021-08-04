package com.ydw.schedule.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.schedule.dao.TbDevicesMapper;
import com.ydw.schedule.model.db.TbDevices;
import com.ydw.schedule.model.enums.DeviceStatusEnum;
import com.ydw.schedule.model.vo.ResultInfo;
import com.ydw.schedule.service.IDeviceService;
import com.ydw.schedule.service.IQueueService;
import com.ydw.schedule.util.ThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl extends ServiceImpl<TbDevicesMapper, TbDevices> implements IDeviceService{
	
	Logger logger  = LoggerFactory.getLogger(getClass());

	@Autowired
	private IQueueService queueService;
	
	@Autowired
	private AbstractScheduleService scheduleServiceImpl;

	@Autowired
	private ThreadPool threadPool;

	/**
	 * 上传状态，涉及资源重置调度
	 */
	@Override
	public ResultInfo reportStatus(String deviceId, Integer status) {
		//可能是deviceId或者mac
		QueryWrapper<TbDevices> qw = new QueryWrapper<>();
        qw.eq("id",deviceId);
		qw.eq("valid",true);
		TbDevices devices = this.getOne(qw);
		DeviceStatusEnum e = DeviceStatusEnum.getEnum(status);
		if(devices == null || e == null){
			logger.error("上报状态接口，参数不合法！无法找到此设备或者此状态，设备id-{}，上报status-{}", deviceId, status);
			return ResultInfo.fail("上报状态接口，参数不合法！无法找到此设备或者此状态！");
		}
		if(e == DeviceStatusEnum.IDLE){
			if(devices.getSchStatus()){
				//重新加入调度
				logger.info("设备{}状态为idle且调度状态为true，触发加入调度！", devices.getId());
				scheduleServiceImpl.attachSchedule(devices.getClusterId(), devices.getId());
				threadPool.submit(() -> queueService.consumeQueue(devices.getClusterId()));
			}else{
				logger.info("设备{}状态为idle但调度状态为false，触发移除调度！", devices.getId());
				scheduleServiceImpl.detachSchedule(devices.getClusterId(), devices.getId());
			}
		}else{
			//状态改变后触发移除调度
			if(devices.getStatus() != status){
				logger.info("设备{}状态为{}，触发移除调度！", devices.getId(), status);
				scheduleServiceImpl.detachSchedule(devices.getClusterId(), devices.getId());
			}
		}
		if(devices.getStatus() != status){
			UpdateWrapper<TbDevices> uw = new UpdateWrapper<>();
			uw.eq("id", devices.getId());
			uw.set("status", status);
			update(uw);
		}
		return ResultInfo.success();
	}

	@Override
	public ResultInfo installedApp(String deviceId, String appId) {
		TbDevices devices = getById(deviceId);
		scheduleServiceImpl.addAppToDevice(devices.getClusterId(), appId, deviceId);
		return ResultInfo.success();
	}

	@Override
	public ResultInfo uninstalledApp(String deviceId, String appId) {
		TbDevices devices = getById(deviceId);
		scheduleServiceImpl.delAppFromDevice(devices.getClusterId(), appId, deviceId);
		return ResultInfo.success();
	}

}
