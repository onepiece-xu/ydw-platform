package com.ydw.admin.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

public class UserVO {

    private static final long serialVersionUID = 1L;

    private String id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 手机号
     */
    private String mobileNumber;
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
    private BigDecimal userBalance;
    /**
     * 注册时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registerTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifiedTime;
    /**
     * 头像地址
     */
    private String avatar;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loginTime;

    private String accountStatus;
    private Boolean onilneStatus;
    private String token;
    private String wechat;
    private String qqNumber;
    private String gender;
    private String recommender;
    private String wechatOpenId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public BigDecimal getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(BigDecimal userBalance) {
        this.userBalance = userBalance;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id='" + id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPoint=" + userPoint +
                ", userLevel=" + userLevel +
                ", userBalance=" + userBalance +
                ", registerTime=" + registerTime +
                ", modifiedTime=" + modifiedTime +
                ", avatar='" + avatar + '\'' +
                ", loginTime=" + loginTime +
                ", accountStatus='" + accountStatus + '\'' +
                ", onilneStatus=" + onilneStatus +
                ", token='" + token + '\'' +
                ", wechat='" + wechat + '\'' +
                ", qqNumber='" + qqNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", recommender='" + recommender + '\'' +
                ", wechatOpenId='" + wechatOpenId + '\'' +
                '}';
    }
}
