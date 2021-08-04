package com.ydw.admin.model.db;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户支付绑定
 * </p>
 *
 * @author xulh
 * @since 2021-01-04
 */
public class UserPay implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 收款人
     */
    private String payee;

    /**
     * 收款人名称
     */
    private String payeeName;

    /**
     * 支付账号
     */
    private String payAccount;

    /**
     * 支付类型（1：支付宝，2：微信）
     */
    private Integer payType;

    /**
     * 是否默认支付方式
     */
    private Boolean isdefault;

    /**
     * 绑定时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 是否有效
     */
    private Boolean valid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Boolean getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(Boolean isdefault) {
        this.isdefault = isdefault;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "UserPay{" +
        "id=" + id +
        ", payee=" + payee +
        ", payeeName=" + payeeName +
        ", payAccount=" + payAccount +
        ", payType=" + payType +
        ", isdefault=" + isdefault +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", valid=" + valid +
        "}";
    }
}
