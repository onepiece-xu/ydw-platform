package com.ydw.admin.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xulh
 * @description: TODO
 * @date 2021/2/213:14
 */
public class RechargeSummaryVO implements Serializable {

    /**
     * 充值次数
     */
    private long rechargeCount;

    /**
     * 充值金额
     */
    private BigDecimal rechargeAmount;

    public long getRechargeCount() {
        return rechargeCount;
    }

    public void setRechargeCount(long rechargeCount) {
        this.rechargeCount = rechargeCount;
    }

    public BigDecimal getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(BigDecimal rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }
}
