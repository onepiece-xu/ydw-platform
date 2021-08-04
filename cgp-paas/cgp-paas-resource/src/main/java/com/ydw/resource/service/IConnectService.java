package com.ydw.resource.service;

import com.ydw.resource.model.vo.*;
import com.ydw.resource.utils.ResultInfo;

public interface IConnectService {

	ResultInfo apply(ApplyParams param);

	ResultInfo release(ConnectVO vo);

	ResultInfo abnormal(String errCode, String connectId);

	ResultInfo getUserConnectStatus(String account);

    ResultInfo connect(ConnectParams param);

	ResultInfo reconnect(ReConnectVO vo);

	void forceRelease(String deviceId);

	ResultInfo normal(String connectId);

	ResultInfo getConnectDetail(String connectId);

	ResultInfo openApp(DeviceInfo deviceInfo, AppInfo appInfo, String userId, Object rentalParams);

    ResultInfo openApp(AppOperateParams param);

	ResultInfo closeApp(DeviceInfo deviceInfo, AppInfo appInfo, String userId);

	ResultInfo closeApp(AppOperateParams param);

    ResultInfo noticeResourceApplyed(ConnectVO vo);

	ResultInfo getRank(String account);

	ResultInfo cancelConnect(ConnectParams param);

	ResultInfo queueOut(String account);
}
