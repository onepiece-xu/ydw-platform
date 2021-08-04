package com.ydw.resource.service;

import java.util.List;

import com.ydw.resource.model.vo.DeviceAppOperateResultVO;
import com.ydw.resource.utils.ResultInfo;

public interface IDeviceAppsService {

	ResultInfo installToDevices(String appId, List<String> deviceIds);

	ResultInfo unInstallFromDevices(String appId, List<String> deviceIds);

	void checkDownApps(String deviceId);

	ResultInfo reportDeviceAppResult(DeviceAppOperateResultVO vo);

    ResultInfo startApp(List<String> deviceIds, String appId);

	ResultInfo stopApp(List<String> deviceIds, String appId);
}
