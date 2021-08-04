package com.ydw.edge.service;

import com.ydw.edge.model.vo.*;

public interface  IDeviceService {

    ResultInfo reboot(DeviceOperateParam param);
    ResultInfo rebuild(DeviceOperateParam param);
    ResultInfo shutdown(DeviceOperateParam param);
    ResultInfo open( DeviceOperateParam param);
	ResultInfo initDeviceInfo(DeviceOperateParam param);
	
	ResultInfo startStream(StreamOperateParam param);
    ResultInfo stopStream(StreamOperateParam param);

    ResultInfo startApp(AppOperateParam param);
    ResultInfo stopApp(AppOperateParam param);
    ResultInfo install(AppOperateParam param);
    ResultInfo unInstall(AppOperateParam param);
}
