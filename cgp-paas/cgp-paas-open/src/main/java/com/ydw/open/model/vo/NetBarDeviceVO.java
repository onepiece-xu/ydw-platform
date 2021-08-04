package com.ydw.open.model.vo;

public class NetBarDeviceVO {

    private String id;

    private String name;

    private String ip;

    private Integer type;

    /**
     * 设备状态:
     * 1 IDLE 空闲
     * 2 USED使用
     * 3 ERROR错误
     * 4 INSTALLING 安装中
     * 5 REBOOTING  重启中
     */
    private Integer status;

    private Boolean schStatus;

    private String netbarName;

    private String netbarId;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getSchStatus() {
        return schStatus;
    }

    public void setSchStatus(Boolean schStatus) {
        this.schStatus = schStatus;
    }

    public String getNetbarName() {
        return netbarName;
    }

    public void setNetbarName(String netbarName) {
        this.netbarName = netbarName;
    }

    public String getNetbarId() {
        return netbarId;
    }

    public void setNetbarId(String netbarId) {
        this.netbarId = netbarId;
    }
}
