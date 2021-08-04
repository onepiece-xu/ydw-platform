package com.ydw.open.model.vo;

public class DeviceUsageCountHistoryVO {

    private String totalTime;

    private String clusterName;

    private String deviceCount;

    private String deviceUsage;

    private String clusterId;

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }


    public String getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(String deviceCount) {
        this.deviceCount = deviceCount;
    }

    public String getDeviceUsage() {
        return deviceUsage;
    }

    public void setDeviceUsage(String deviceUsage) {
        this.deviceUsage = deviceUsage;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }
}
