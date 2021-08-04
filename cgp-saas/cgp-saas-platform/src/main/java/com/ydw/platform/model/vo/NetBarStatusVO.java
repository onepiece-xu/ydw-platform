package com.ydw.platform.model.vo;

public class NetBarStatusVO {
    private String clusterId;
    private String netbarId;

    /**
     * 状态 空闲 1  拥挤 2  繁忙 3
     */
    private String Status;

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getNetbarId() {
        return netbarId;
    }

    public void setNetbarId(String netbarId) {
        this.netbarId = netbarId;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
