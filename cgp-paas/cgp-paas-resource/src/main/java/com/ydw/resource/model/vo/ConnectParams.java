package com.ydw.resource.model.vo;

public class ConnectParams extends SaasCommonParams {
    /**
     * 连接id
     */
    private String connectId;
    /**
     * 设备id
     */
    private String deviceId;
    /**
     * 应用id
     */
    private String appId;
    /**
     * 客户端类型, 0-移动客户端， 1-pc客户端，用于设置不同的流策略, 默认为 0
     */
    private Integer client;
    /**
     * 客户端类型 ,0-apk / 1-webrtc
     */
    private Integer type;
    /**
     * 用户ip
     */
    private String customIp;
    /**
     * 租号
     */
    private Object rentalParams;

    public String getConnectId() {
        return connectId;
    }

    public void setConnectId(String connectId) {
        this.connectId = connectId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCustomIp() {
        return customIp;
    }

    public void setCustomIp(String customIp) {
        this.customIp = customIp;
    }

    public Object getRentalParams() {
        return rentalParams;
    }

    public void setRentalParams(Object rentalParams) {
        this.rentalParams = rentalParams;
    }

    @Override
    public String toString() {
        return "ConnectParams{" +
                "connectId='" + connectId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", appId='" + appId + '\'' +
                ", client=" + client +
                ", type=" + type +
                ", customIp='" + customIp + '\'' +
                ", rentalParams=" + rentalParams +
                '}';
    }
}
