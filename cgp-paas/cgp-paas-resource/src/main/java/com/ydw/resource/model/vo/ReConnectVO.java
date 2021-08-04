package com.ydw.resource.model.vo;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/11/613:21
 */
public class ReConnectVO extends SaasCommonParams {

    private String connectId;

    private String deviceId;

    private String appId;
    /**
     * 连接类型 ,0-apk / 1-webrtc
     */
    private int type;
    /**
     * 客户端类型, 0-移动客户端， 1-pc客户端，用于设置不同的流策略, 默认为 0
     */
    private int client;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

}
