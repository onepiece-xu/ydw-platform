package com.ydw.open.model.vo;

import java.util.Date;

public class AppTagVO {

    private String appName;
    private String appId;
    private String tagName;
    private String description;
    private Date uploadTime;
    private Integer Type;

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(Integer type) {
        Type = type;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (object instanceof AppTagVO) {
            AppTagVO app = (AppTagVO) object;
            return this.appId.equals(app.appId)
                    && this.appName.equals(app.appName);
        }
        return super.equals(object);
    }
}
