package com.ydw.edge.service;

import com.ydw.edge.model.vo.DeviceStatus;
import com.ydw.edge.model.vo.ResultInfo;

public interface IDeviceStatusService {

    //上报状态
    ResultInfo updateStatus(DeviceStatus status);

    //连接异常上报
    ResultInfo abnormal (String errCode, String connectId);

    //连接正常上报
    ResultInfo normal(String connectId);
}
