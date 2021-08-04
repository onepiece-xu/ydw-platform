package com.ydw.user.model.vo;


import com.ydw.user.model.db.TbKeyConfig;

public class KeyConfigVO extends TbKeyConfig {

    private  String appName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
