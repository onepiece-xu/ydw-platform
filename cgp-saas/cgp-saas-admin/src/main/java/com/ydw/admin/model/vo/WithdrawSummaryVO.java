package com.ydw.admin.model.vo;

import java.io.Serializable;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/2417:40
 */
public class WithdrawSummaryVO implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 申请总次数
     */
    private int withdrawApplyNum;

    /**
     * 申请总金额
     */
    private int withdrawApplySum;

    /**
     * 提现成功金额
     */
    private int withdrawSum;

    /**
     * 提现成功
     */
    private int withdrawNum;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getWithdrawApplyNum() {
        return withdrawApplyNum;
    }

    public void setWithdrawApplyNum(int withdrawApplyNum) {
        this.withdrawApplyNum = withdrawApplyNum;
    }

    public int getWithdrawApplySum() {
        return withdrawApplySum;
    }

    public void setWithdrawApplySum(int withdrawApplySum) {
        this.withdrawApplySum = withdrawApplySum;
    }

    public int getWithdrawSum() {
        return withdrawSum;
    }

    public void setWithdrawSum(int withdrawSum) {
        this.withdrawSum = withdrawSum;
    }

    public int getWithdrawNum() {
        return withdrawNum;
    }

    public void setWithdrawNum(int withdrawNum) {
        this.withdrawNum = withdrawNum;
    }
}
