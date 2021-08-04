package com.ydw.schedule.service.impl;

import com.ydw.schedule.model.db.TbDeviceApps;
import com.ydw.schedule.dao.TbDeviceAppsMapper;
import com.ydw.schedule.service.ITbDeviceAppsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-05-11
 */
@Service
public class TbDeviceAppsServiceImpl extends ServiceImpl<TbDeviceAppsMapper, TbDeviceApps> implements ITbDeviceAppsService {

	@Autowired
	private TbDeviceAppsMapper tbDeviceAppsMapper;
	
	@Override
	public List<String> getDeviceByCluAndApp(String clusterId ,String appId) {
		return tbDeviceAppsMapper.getTbDeviceAppsByApp(clusterId, appId);
	}

}
