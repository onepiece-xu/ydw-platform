package com.ydw.meterage.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.meterage.dao.TbDeviceUsedMapper;
import com.ydw.meterage.model.db.TbDeviceUsed;
import com.ydw.meterage.model.vo.ResultInfo;
import com.ydw.meterage.service.ITbDeviceUsedService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-05-11
 */
@Service
public class TbDeviceUsedServiceImpl extends ServiceImpl<TbDeviceUsedMapper, TbDeviceUsed> implements ITbDeviceUsedService {
	
	@Override
	public ResultInfo beginUse(TbDeviceUsed vo) {
		//开始计量
		LocalDateTime now = LocalDateTime.now();
		TbDeviceUsed tbDeviceUsed = new TbDeviceUsed();
		tbDeviceUsed.setAppId(vo.getAppId());
		tbDeviceUsed.setBeginTime(now);
		tbDeviceUsed.setCustomId(vo.getCustomId());
		tbDeviceUsed.setDeviceId(vo.getDeviceId());
		tbDeviceUsed.setEnterpriseId(vo.getEnterpriseId());
		tbDeviceUsed.setFromIp(vo.getFromIp());
		tbDeviceUsed.setId(vo.getConnectId());
		tbDeviceUsed.setClient(vo.getClient());
		tbDeviceUsed.setType(vo.getType());
		tbDeviceUsed.setSaas(vo.getSaas());
		this.save(tbDeviceUsed);
		return ResultInfo.success();
	}

	@Override
	public ResultInfo endUse(String connectId) {
		//结束计量
		TbDeviceUsed deviceUsed = this.getById(connectId);
		deviceUsed.setEndTime(LocalDateTime.now());
		deviceUsed.setTotalTime((int)Duration.between(deviceUsed.getBeginTime(), deviceUsed.getEndTime()).getSeconds());
		this.updateById(deviceUsed);
		return ResultInfo.success();
	}

	@Override
	public ResultInfo getConnectDetail(String connectId) {
		TbDeviceUsed deviceUsed = this.getById(connectId);
		if(deviceUsed != null){
			return ResultInfo.success(deviceUsed);
		}else{
			return ResultInfo.fail("无此连接！connectId="+connectId);
		}
	}

	@Override
	public ResultInfo updateConnectMethod(String connectId, int client, int type) {
		UpdateWrapper<TbDeviceUsed> uw = new UpdateWrapper<>();
		uw.eq("id",connectId);
		uw.set("client",client);
		uw.set("type",type);
		boolean update = update(uw);
		if (update){
			return ResultInfo.success();
		}else{
			return ResultInfo.fail();
		}
	}

}
