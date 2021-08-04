package com.ydw.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.resource.dao.DeviceUsedMapper;
import com.ydw.resource.model.db.DeviceUsed;
import com.ydw.resource.service.IDeviceUsedService;
import com.ydw.resource.utils.ResultInfo;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-05-11
 */
@Service
public class DeviceUsedServiceImpl extends ServiceImpl<DeviceUsedMapper, DeviceUsed> implements IDeviceUsedService {
	
	@Override
	public boolean endUse(String connectId) {
		//结束计量
		DeviceUsed deviceUsed = this.getById(connectId);
		if (deviceUsed == null){
			return false;
		}
		deviceUsed.setEndTime(LocalDateTime.now());
		deviceUsed.setTotalTime((int)Duration.between(deviceUsed.getBeginTime(), deviceUsed.getEndTime()).getSeconds());
		return this.updateById(deviceUsed);
	}

	@Override
	public void updateConnectMethod(String connectId, int client, int type) {
		UpdateWrapper<DeviceUsed> uw = new UpdateWrapper<>();
		uw.eq("id",connectId);
		uw.set("client",client);
		uw.set("type",type);
		update(uw);
	}

}
