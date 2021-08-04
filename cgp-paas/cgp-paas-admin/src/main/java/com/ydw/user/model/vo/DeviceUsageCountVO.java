package com.ydw.user.model.vo;

public class DeviceUsageCountVO {

    private String  clusterName;

    private String connectCount;

    private String  deviceCount;

    private String  percentage;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getConnectCount() {
        return connectCount;
    }

    public void setConnectCount(String connectCount) {
        this.connectCount = connectCount;
    }

    public String getDeviceCount() {
        return deviceCount;
    }

    public void setDeviceCount(String deviceCount) {
        this.deviceCount = deviceCount;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
