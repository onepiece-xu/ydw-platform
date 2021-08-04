package com.ydw.user.model.vo;

public class DemoAppVO {
    private String  appId;
    private String appName;
    private Integer  type;
    private Integer orderNumber;
    private Integer appType;
    private Integer screen;
//    private  String identification;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }
    //    public String getIdentification() {
//        return identification;
//    }
//
//    public void setIdentification(String identification) {
//        this.identification = identification;
//    }

    public Integer getScreen() {
        return screen;
    }

    public void setScreen(Integer screen) {
        this.screen = screen;
    }
}
