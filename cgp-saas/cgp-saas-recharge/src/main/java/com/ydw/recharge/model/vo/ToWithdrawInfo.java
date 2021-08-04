package com.ydw.recharge.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/2516:33
 */
public class ToWithdrawInfo implements Serializable {

    /**
     * 可提现余额
     */
    private BigDecimal balance;

    /**
     * 绑定的支付id
     */
    private String payId;

    /**
     * 收款人姓名
     */
    private String payeeName;

    /**
     * 支付账号
     */
    private String payAccount;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
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
}
