package com.ydw.resource.model.vo;

import java.io.Serializable;

public class DeviceInfo implements Serializable{

	/**
     * 主键，自动生成的不重复ID
     */
    private String deviceId;

    /**
     * 资源名称
     */
    private String deviceName;

    /**
     * 服务器管理口IP，用于扩展

     */
    private String managerIp;

    /**
     * 服务器管理端口

     */
    private Integer managerPort;

    /**
     * 内部IP地址
     */
    private String innerIp;

    /**
     * 内网管理端口
     */
    private Integer innerPort;
    
    /**
     * adb连接地址
     */
    private String adbIp;
    
    /**
     * 内网管理端口
     */
    private Integer adbPort;
    
    /**
     * srt端口
     */
    private Integer srtPort;

    /**
     * 外网访问IP，可以是路由器Nat地址
     */
    private String ip;

    /**
     * 外部访问端口，可以是路由器Nat端口
     */
    private Integer port;

    /**
     * 设备状态:
	 * 1 IDLE 空闲 2 USED使用  3 ERROR错误 4 INSTALLING 安装中  5 REBOOTING  重启中  6 DELETED 删除
     */
    private Integer status;

	/**
	 * 设备uuid，瑞云专用
	 */
	private String uuid;

    /**
     * 已用空间
     */
    private Integer usedSpace;

    /**
     * 可用空间
     */
    private Integer freeSpace;

    /**
     * 内部mac地址
     */
    private String innerMac;

	/**
     * 集群id
     */
    private String clusterId;

    /**
     * 集群名称
     */
    private String clusterName;

    /**
     * Api url地址，用于访问边缘节点的api。
     */
    private String apiUrl;

	/**
	 * 设备用于访问边缘节点的api。
	 */
	private String nodeUrl;

    /**
     * 节点主IP，用于外部访问的主IP，一般是负载IP，兼容ipv6地址
     */
    private String accessIp;
    
    /**
     * 自增ID
     */
    private Integer baseId;

    /**
     * 名称
     */
    private String baseName;

    /**
     * 资源类型 0-arm   1-x86(pcfarm)
     */
    private Integer deviceType;
    
    /**
     * 上传文件路径
     */
    private String sdPath;

    /**
     * 设备型号
     */
    private String model;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getManagerIp() {
		return managerIp;
	}

	public void setManagerIp(String managerIp) {
		this.managerIp = managerIp;
	}

	public Integer getManagerPort() {
		return managerPort;
	}

	public void setManagerPort(Integer managerPort) {
		this.managerPort = managerPort;
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getUsedSpace() {
		return usedSpace;
	}

	public void setUsedSpace(Integer usedSpace) {
		this.usedSpace = usedSpace;
	}

	public Integer getFreeSpace() {
		return freeSpace;
	}

	public void setFreeSpace(Integer freeSpace) {
		this.freeSpace = freeSpace;
	}

	public String getInnerMac() {
		return innerMac;
	}

	public void setInnerMac(String innerMac) {
		this.innerMac = innerMac;
	}

	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public String getAccessIp() {
		return accessIp;
	}

	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

	public Integer getBaseId() {
		return baseId;
	}

	public void setBaseId(Integer baseId) {
		this.baseId = baseId;
	}

	public String getBaseName() {
		return baseName;
	}

	public void setBaseName(String baseName) {
		this.baseName = baseName;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getAdbPort() {
		return adbPort;
	}

	public void setAdbPort(Integer adbPort) {
		this.adbPort = adbPort;
	}

	public String getAdbIp() {
		return adbIp;
	}

	public void setAdbIp(String adbIp) {
		this.adbIp = adbIp;
	}

	public String getSdPath() {
		return sdPath;
	}

	public void setSdPath(String sdPath) {
		this.sdPath = sdPath;
	}

	public Integer getSrtPort() {
		return srtPort;
	}

	public void setSrtPort(Integer srtPort) {
		this.srtPort = srtPort;
	}

    public String getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
