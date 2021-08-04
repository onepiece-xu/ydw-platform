package com.ydw.admin.model.vo;

public class UploadPictureVO {
    /**
     * 图片对应的appid
     */
    private String appId;

    /**
     * 图片的类型
     */
    private Integer number;

    private String pictureUrl;

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
