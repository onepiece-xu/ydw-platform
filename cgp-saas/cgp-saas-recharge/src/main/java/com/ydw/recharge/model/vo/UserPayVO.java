package com.ydw.recharge.model.vo;

import java.io.Serializable;

/**
 * <p>
 * 用户支付绑定
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
public class UserPayVO implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 绑定记录id
     */
    private String id;

    /**
     * 收款人
     */
    private String payee;

    /**
     * 收款人姓名
     */
    private String payeeName;

    /**
     * 支付账号
     */
    private String payAccount;

    /**
     * 支付类型（1：支付宝，2：微信）
     */
    private Integer payType = 1;

    /**
     * 手机号
     */
    private String mobileNumber;

    /**
     * 本次绑定验证码
     */
    private String validateCode;

    /**
     * 1绑定2修改绑定
     */
    private int type = 1;

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

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
