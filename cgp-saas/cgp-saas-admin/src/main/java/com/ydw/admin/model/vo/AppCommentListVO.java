package com.ydw.admin.model.vo;

import com.ydw.admin.model.db.AppComment;

public class AppCommentListVO extends AppComment {

    private String nickName;
    private String appName;
    private String mobileNumber;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
