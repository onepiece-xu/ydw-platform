package com.ydw.platform.model.vo;

import java.io.Serializable;

/**
 * @author xulh
 * @description: TODO
 * @date 2021/3/313:11
 */
public class OpenAndCloseAppParameter implements Serializable {

    public OpenAndCloseAppParameter() {
    }

    public OpenAndCloseAppParameter(String deviceId, String userId, String appId,
                                    String connectId, Object rentalParams) {
        this.deviceId = deviceId;
        this.userId = userId;
        this.appId = appId;
        this.connectId = connectId;
        this.rentalParams = rentalParams;
    }

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * appId
     */
    private String appId;

    /**
     * 连接id
     */
    private String connectId;

    /**
     * 租号参数
     */
    private Object rentalParams;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getConnectId() {
        return connectId;
    }

    public void setConnectId(String connectId) {
        this.connectId = connectId;
    }

    public Object getRentalParams() {
        return rentalParams;
    }

    public void setRentalParams(Object rentalParams) {
        this.rentalParams = rentalParams;
    }

    @Override
    public String toString() {
        return "OpenAndCloseAppParameter{" +
                "deviceId='" + deviceId + '\'' +
                ", userId='" + userId + '\'' +
                ", appId='" + appId + '\'' +
                ", connectId='" + connectId + '\'' +
                ", rentalParams=" + rentalParams +
                '}';
    }
}
