package com.ydw.schedule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.schedule.model.db.TbDevices;
import com.ydw.schedule.model.vo.ResultInfo;

/**
 * 设备操作
 * @author xulh
 *
 */
public interface IDeviceService extends IService<TbDevices>{
	
	/**
	 * 上报状态
	 * @param deviceId
	 * @param status
	 * @return
	 */
	ResultInfo reportStatus(String deviceId, Integer status);

	/**
	 * app安装成功
	 * @param deviceId
	 * @param appId
	 * @return
	 */
	ResultInfo installedApp(String deviceId, String appId);

	/**
	 * app卸载成功
	 * @param deviceId
	 * @param appId
	 * @return
	 */
	ResultInfo uninstalledApp(String deviceId, String appId);
}
