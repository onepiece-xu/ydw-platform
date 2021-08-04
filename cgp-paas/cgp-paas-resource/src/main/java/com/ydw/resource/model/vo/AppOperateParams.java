package com.ydw.resource.model.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xulh
 * @description: TODO
 * @date 2021/3/313:11
 */
public class AppOperateParams implements Serializable {

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * 用户id
     */
    private String account;

    /**
     * appId
     */
    private String appId;

    /**
     * 连接id
     */
    private String connectId;

    /**
     * 租号
     */
    private Object rentalParams;

    public AppOperateParams() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
}
