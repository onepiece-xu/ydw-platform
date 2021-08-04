package com.ydw.schedule.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.schedule.model.db.TbDeviceApps;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-05-11
 */
public interface ITbDeviceAppsService extends IService<TbDeviceApps> {

	List<String> getDeviceByCluAndApp(String clusterId, String appId);
}
