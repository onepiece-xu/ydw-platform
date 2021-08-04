package com.ydw.admin.model.vo;

import java.util.List;

public class CheckTagsVO {
    private  String  appId;

    private List<Integer> ids;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }
}
