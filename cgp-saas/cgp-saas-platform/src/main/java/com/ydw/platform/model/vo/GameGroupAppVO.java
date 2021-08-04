package com.ydw.platform.model.vo;

public class GameGroupAppVO {
    private  Integer id;
    private String AppName;

    private String AppId;

    private  String  description;
    private  Integer gameOrder;
    private String weight1;
    private String weight2;
    private String weight3;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGameOrder() {
        return gameOrder;
    }

    public void setGameOrder(Integer gameOrder) {
        this.gameOrder = gameOrder;
    }

    public String getWeight1() {
        return weight1;
    }

    public void setWeight1(String weight1) {
        this.weight1 = weight1;
    }

    public String getWeight2() {
        return weight2;
    }

    public void setWeight2(String weight2) {
        this.weight2 = weight2;
    }

    public String getWeight3() {
        return weight3;
    }

    public void setWeight3(String weight3) {
        this.weight3 = weight3;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
