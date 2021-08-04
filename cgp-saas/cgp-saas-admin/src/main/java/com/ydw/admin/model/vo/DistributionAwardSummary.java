package com.ydw.admin.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/2418:07
 */
public class DistributionAwardSummary implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户余额
     */
    private BigDecimal balance;

    /**
     * 用户总收益
     */
    private BigDecimal profit;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getProfit() {
        return profit;
    }

    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }
}
