package com.ydw.admin.model.vo;

import com.ydw.admin.model.db.WithdrawRecord;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/259:26
 */
public class WithdrawVO extends WithdrawRecord {

    /**
     * 提款人名称
     */
    private String payeeName;

    /**
     * 用户当前余额
     */
    private String balance;

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
