package com.ydw.resource.model.db;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2020-07-30
 */
@TableName("tb_devices")
public class Devices implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键，自动生成的不重复ID
     */
    private String id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 设备规格编号
     */
    private Integer baseId;

    /**
     * 集群编号
     */
    private String clusterId;

    /**
     * IDC机房

     */
    private String idc;

    /**
     * 机架编号

     */
    private String cabinet;

    /**
     * 服务器机架槽位

     */
    private String location;

    /**
     * 设备所在服务器内部槽位

     */
    private String slot;

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
     * adb管理端口
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
        1 IDLE 空闲
        2 USED使用
        3 ERROR错误
        4 INSTALLING 安装中
        5 REBOOTING  重启中
        6 DELETED 删除
     */
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

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
     * 描述
     */
    private String description;

    /**
     * 可调度
     */
    private Boolean schStatus;

    /**
     * 描述
     */
    private String sdPath;
    
    /**
     * 已注册
     */
    private Boolean init;

    private String gateway;

    /**
     * 是否有效 :     0无效 1有效（软删除）默认1
     */
    private Boolean valid;

    /**
     * 设备的uuid（瑞云设备专用）
     */
    private String uuid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBaseId() {
        return baseId;
    }

    public void setBaseId(Integer baseId) {
        this.baseId = baseId;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getIdc() {
        return idc;
    }

    public void setIdc(String idc) {
        this.idc = idc;
    }

    public String getCabinet() {
        return cabinet;
    }

    public void setCabinet(String cabinet) {
        this.cabinet = cabinet;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getSchStatus() {
        return schStatus;
    }

    public void setSchStatus(Boolean schStatus) {
        this.schStatus = schStatus;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Boolean getInit() {
		return init;
	}

	public void setInit(Boolean init) {
		this.init = init;
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

    public Integer getSrtPort() {
        return srtPort;
    }

    public void setSrtPort(Integer srtPort) {
        this.srtPort = srtPort;
    }

    public String getSdPath() {
        return sdPath;
    }

    public void setSdPath(String sdPath) {
        this.sdPath = sdPath;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Devices{" +
        "id=" + id +
        ", name=" + name +
        ", baseId=" + baseId +
        ", clusterId=" + clusterId +
        ", idc=" + idc +
        ", cabinet=" + cabinet +
        ", location=" + location +
        ", slot=" + slot +
        ", managerIp=" + managerIp +
        ", managerPort=" + managerPort +
        ", innerIp=" + innerIp +
        ", innerPort=" + innerPort +
        ", ip=" + ip +
        ", port=" + port +
        ", status=" + status +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", usedSpace=" + usedSpace +
        ", freeSpace=" + freeSpace +
        ", innerMac=" + innerMac +
        ", description=" + description +
        ", schStatus=" + schStatus +
        ", valid=" + valid +
        "}";
    }
}
