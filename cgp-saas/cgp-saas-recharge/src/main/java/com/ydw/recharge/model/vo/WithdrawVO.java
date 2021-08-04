package com.ydw.recharge.model.vo;

import com.ydw.recharge.model.db.WithdrawRecord;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/2310:55
 */
public class WithdrawVO extends WithdrawRecord {

    /**
     * 电话号码
     */
    private String mobileNumber;

    /**
     * 本次绑定验证码
     */
    private String validateCode;

    /**
     * 提现账号
     */
    private String payAccount;

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

    public String getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(String payAccount) {
        this.payAccount = payAccount;
    }
}
