package com.ydw.admin.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class DailyReportVO  {

    /**
     * 该记录对应的日期
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private String dateTime;

    /**
     * 当日新增注册用户
     */
    private Integer newRegisterCount;

    /**
     * 当前总注册用户
     */
    private Integer totalUserCount;

    /**
     * 当日用户总付费额
     */
    private BigDecimal totalPayment;

    /**
     * 当日总付费用户数
     */
    private Integer userPaymentCount;

    /**
     * 当日新增用户付费额
     */
    private BigDecimal newUserPayment;

    /**
     * 当日新增付费用户数
     */
    private Integer newPaymentCount;

    /**
     * 当日总登录用户数
     */
    private Integer totalLogin;

    /**
     * 当日连接用户总数
     */
    private Integer totalConnect;

    private BigDecimal registeredUserPayment;

    private BigDecimal newUserPaymentRate;

    private BigDecimal  userTotalPaymentRate;

    private BigDecimal activityRate;

    public BigDecimal getActivityRate() {
        return activityRate;
    }

    public void setActivityRate(BigDecimal activityRate) {
        this.activityRate = activityRate;
    }

    public BigDecimal getUserTotalPaymentRate() {
        return userTotalPaymentRate;
    }

    public void setUserTotalPaymentRate(BigDecimal userTotalPaymentRate) {
        this.userTotalPaymentRate = userTotalPaymentRate;
    }

    public BigDecimal getNewUserPaymentRate() {
        return newUserPaymentRate;
    }

    public void setNewUserPaymentRate(BigDecimal newUserPaymentRate) {
        this.newUserPaymentRate = newUserPaymentRate;
    }

    public BigDecimal getRegisteredUserPayment() {
        return registeredUserPayment;
    }

    public void setRegisteredUserPayment(BigDecimal registeredUserPayment) {
        this.registeredUserPayment = registeredUserPayment;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getNewRegisterCount() {
        return newRegisterCount;
    }

    public void setNewRegisterCount(Integer newRegisterCount) {
        this.newRegisterCount = newRegisterCount;
    }

    public Integer getTotalUserCount() {
        return totalUserCount;
    }

    public void setTotalUserCount(Integer totalUserCount) {
        this.totalUserCount = totalUserCount;
    }

    public BigDecimal getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(BigDecimal totalPayment) {
        this.totalPayment = totalPayment;
    }

    public Integer getUserPaymentCount() {
        return userPaymentCount;
    }

    public void setUserPaymentCount(Integer userPaymentCount) {
        this.userPaymentCount = userPaymentCount;
    }

    public BigDecimal getNewUserPayment() {
        return newUserPayment;
    }

    public void setNewUserPayment(BigDecimal newUserPayment) {
        this.newUserPayment = newUserPayment;
    }

    public Integer getNewPaymentCount() {
        return newPaymentCount;
    }

    public void setNewPaymentCount(Integer newPaymentCount) {
        this.newPaymentCount = newPaymentCount;
    }

    public Integer getTotalLogin() {
        return totalLogin;
    }

    public void setTotalLogin(Integer totalLogin) {
        this.totalLogin = totalLogin;
    }

    public Integer getTotalConnect() {
        return totalConnect;
    }

    public void setTotalConnect(Integer totalConnect) {
        this.totalConnect = totalConnect;
    }
}
