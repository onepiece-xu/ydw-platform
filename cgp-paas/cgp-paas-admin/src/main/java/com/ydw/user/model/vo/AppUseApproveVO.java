package com.ydw.user.model.vo;

public class AppUseApproveVO {
    private Integer id;

    private  String appName;
    private String userName;

    private String appId;
    private String enterpriseId;
    private Integer size;
    private Integer type;
    private Integer realSize;

    /**
     * 0审批中 1成功 2审批失败
     */
    private Integer valid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRealSize() {
        return realSize;
    }

    public void setRealSize(Integer realSize) {
        this.realSize = realSize;
    }

    @Override
    public String toString() {
        return "AppUseApproveVO{" +
                "id=" + id +
                ", appName='" + appName + '\'' +
                ", userName='" + userName + '\'' +
                ", appId='" + appId + '\'' +
                ", enterpriseId='" + enterpriseId + '\'' +
                ", size=" + size +
                ", type=" + type +
                ", realSize=" + realSize +
                ", valid=" + valid +
                '}';
    }
}
