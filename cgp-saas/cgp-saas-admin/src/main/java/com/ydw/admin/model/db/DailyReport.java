package com.ydw.admin.model.db;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-03-02
 */
public class DailyReport implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 该记录对应的日期
     */
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date dateTime;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
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

    @Override
    public String toString() {
        return "DailyReport{" +
        "id=" + id +
        ", dateTime=" + dateTime +
        ", newRegisterCount=" + newRegisterCount +
        ", totalUserCount=" + totalUserCount +
        ", totalPayment=" + totalPayment +
        ", userPaymentCount=" + userPaymentCount +
        ", newUserPayment=" + newUserPayment +
        ", newPaymentCount=" + newPaymentCount +
        ", totalLogin=" + totalLogin +
        ", totalConnect=" + totalConnect +
        "}";
    }
}
