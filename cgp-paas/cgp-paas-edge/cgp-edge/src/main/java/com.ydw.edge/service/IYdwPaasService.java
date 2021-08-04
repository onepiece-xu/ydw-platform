package com.ydw.edge.service;

import com.ydw.edge.model.vo.DeviceAppOperateResultVO;

public interface IYdwPaasService {

    /**
     * 获取paas的token
     * @return
     */
    public String getToken();

    /**
     * 上报设备状态
     * @param devId
     * @param macAddr
     * @param status
     * @return
     */
    public String reportDevStatus(String devId, String macAddr, Integer status);

    /**
     * 连接异常上报
     * @param connectId
     * @param errCode
     * @return
     */
    public String abnormal(String connectId, String errCode);

    /**
     * 连接正常上报
     * @param connectId
     * @return
     */
    public String normal(String connectId);


    /**
     * app安装卸载状态上报
     * @param resultvo
     * @return
     */
    public String reportDeviceAppResult(DeviceAppOperateResultVO resultvo);

    /**
     * 修改节点外网ip
     * @param oldIp
     * @param newIp
     * @return
     */
    public String updateClusterIp(String oldIp, String newIp);

    /**
     * 修改设备外网ip
     * @param oldIp
     * @param newIp
     * @return
     */
    public String updateDevicesIp(String oldIp, String newIp);

    String issuedApp(String appId);
}
