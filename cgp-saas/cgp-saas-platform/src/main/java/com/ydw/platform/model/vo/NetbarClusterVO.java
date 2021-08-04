package com.ydw.platform.model.vo;

public class NetbarClusterVO {
    private String clusterId;

    private String netbarId;

    /**
     * 1 空闲 ：  空闲设备大于50%   2 拥挤： 大于20小于50  3繁忙： 小于20%
     */
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
