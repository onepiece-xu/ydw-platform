package com.ydw.resource.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.resource.dao.DevicesMapper;
import com.ydw.resource.model.db.Devices;
import com.ydw.resource.model.vo.DeviceSizeVO;
import com.ydw.resource.utils.ResultInfo;

public interface IDeviceService extends IService<Devices> {

	ResultInfo reportStatus(String deviceId, Integer status);

    ResultInfo reportStatus(String deviceId, String macAddr, Integer status);

    ResultInfo reboot(String[] devids);

    ResultInfo rebuild(String[] devids);

    ResultInfo forseRelease(String[] devids);

    ResultInfo shutdown(String[] devids);

    ResultInfo open(String[] devids);

    ResultInfo enable(String[] devids);

    ResultInfo disable(String[] devids);

    ResultInfo setId(String[] devids);

    ResultInfo getId(String devid);

	ResultInfo initDeviceInfo(String[] devid);

	void updateDiskSize(DeviceSizeVO deviceSize);

    ResultInfo updateExternalIp(String oldIp, String newIp);
}
