package com.ydw.recharge.model.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/2517:53
 */
public class WithdrawSummary implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 提现次数
     */
    private int withdrawNum;

    /**
     * 提现总金额
     */
    private BigDecimal withdrawSum;

    /**
     * 数据
     */
    private IPage<WithdrawVO> data;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getWithdrawNum() {
        return withdrawNum;
    }

    public void setWithdrawNum(int withdrawNum) {
        this.withdrawNum = withdrawNum;
    }

    public BigDecimal getWithdrawSum() {
        return withdrawSum;
    }

    public void setWithdrawSum(BigDecimal withdrawSum) {
        this.withdrawSum = withdrawSum;
    }

    public IPage<WithdrawVO> getData() {
        return data;
    }

    public void setData(IPage<WithdrawVO> data) {
        this.data = data;
    }
}
