package com.ydw.edge.model.vo;

import java.io.Serializable;

/**
 * 设备操作参数
 * @author xulh
 * @description: TODO
 * @date 2020/11/515:13
 */
public class DeviceOperateParam implements Serializable {

    /**
     * 设备id
     */
    protected String deviceId;
    /**
     * 设备类型
     */
    protected Integer deviceType;
    /**
     * 设备内网ip
     */
    protected String innerIp;
    /**
     * 设备的内网控制端口
     */
    protected Integer innerPort = 8556;
    /**
     * 设备的srt流端口
     */
    protected Integer srtPort = 8554;
    /**
     * 设备adb连接ip
     */
    protected String adbIp;
    /**
     * 设备adb连接端口
     */
    protected Integer adbPort = 5555;
    /**
     * 设备型号
     */
    protected String model;
    /**
     * 设备的存储地址
     */
    protected String sdPath;
    /**
     * 设备的边缘节点url
     */
    protected String nodeUrl;

    /**
     * 集群id
     */
    protected String clusterId;

    /**
     * 瑞云设备的uuid参数
     */
    protected String uuid;

    public DeviceOperateParam(){}

    public DeviceOperateParam(String deviceId, Integer deviceType, String innerIp, Integer innerPort,
                              Integer srtPort, String adbIp, Integer adbPort, String model, String sdPath, String nodeUrl) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.innerIp = innerIp;
        this.innerPort = innerPort;
        this.srtPort = srtPort;
        this.adbIp = adbIp;
        this.adbPort = adbPort;
        this.model = model;
        this.sdPath = sdPath;
        this.nodeUrl = nodeUrl;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getInnerIp() {
        return innerIp;
    }

    public void setInnerIp(String innerIp) {
        this.innerIp = innerIp;
    }

    public Integer getInnerPort() {
        return innerPort;
    }

    public void setInnerPort(Integer innerPort) {
        this.innerPort = innerPort;
    }

    public Integer getSrtPort() {
        return srtPort;
    }

    public void setSrtPort(Integer srtPort) {
        this.srtPort = srtPort;
    }

    public String getAdbIp() {
        return adbIp;
    }

    public void setAdbIp(String adbIp) {
        this.adbIp = adbIp;
    }

    public Integer getAdbPort() {
        return adbPort;
    }

    public void setAdbPort(Integer adbPort) {
        this.adbPort = adbPort;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSdPath() {
        return sdPath;
    }

    public void setSdPath(String sdPath) {
        this.sdPath = sdPath;
    }

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
