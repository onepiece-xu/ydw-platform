package com.ydw.authentication.model.vo;

import com.ydw.authentication.model.db.UserInfo;

import java.util.Date;

public class UserVO extends UserInfo {

    private static final long serialVersionUID = 1L;

    private  String token;

    private  String enterpriseId;

    private  boolean isOldUser = true;

    /**
     * email
     */
    private String userEmail;
    /**
     * 用户积分
     */
    private Integer userPoint;
    /**
     * 用户等级
     */
    private Integer userLevel;
    /**
     * 平台币余额
     */
    private int userBalance;
    private Date loginTime;
    private String password;
    private String accountStatus;
    private Boolean onilneStatus;

    private String wechat;
    private String qqNumber;
    private String gender;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public boolean getIsOldUser() {
        return isOldUser;
    }

    public void setIsOldUser(boolean isOldUser) {
        this.isOldUser = isOldUser;
    }

    public boolean isOldUser() {
        return isOldUser;
    }

    public void setOldUser(boolean oldUser) {
        isOldUser = oldUser;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(Integer userPoint) {
        this.userPoint = userPoint;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public int getUserBalance() {
      return userBalance;
    }

    public void setUserBalance(int userBalance) {
        this.userBalance = userBalance;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Boolean getOnilneStatus() {
        return onilneStatus;
    }

    public void setOnilneStatus(Boolean onilneStatus) {
        this.onilneStatus = onilneStatus;
    }

    @Override
    public String getWechat() {
        return wechat;
    }

    @Override
    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    @Override
    public String getQqNumber() {
        return qqNumber;
    }

    @Override
    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}


