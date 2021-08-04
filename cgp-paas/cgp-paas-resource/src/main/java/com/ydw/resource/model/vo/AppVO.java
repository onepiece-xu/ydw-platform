package com.ydw.resource.model.vo;

import java.io.Serializable;

public class AppVO implements Serializable {

    /**
     * 自动生成ID
     */
    private String appId;

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 描述
     */
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
