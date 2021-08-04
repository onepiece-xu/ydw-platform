package com.ydw.recharge.model.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ydw.recharge.model.db.DistributionAward;

import java.math.BigDecimal;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/2517:04
 */
public class DistributionAwardVO extends DistributionAward{

    /**
     * 收益笔数
     */
    private int incomeNum;

    /**
     * 总收益金额
     */
    private BigDecimal incomeSum;

    /**
     * 数据
     */
    private IPage<DistributionAwardGroupDayVO> data;


    public int getIncomeNum() {
        return incomeNum;
    }

    public void setIncomeNum(int incomeNum) {
        this.incomeNum = incomeNum;
    }

    public BigDecimal getIncomeSum() {
        return incomeSum;
    }

    public void setIncomeSum(BigDecimal incomeSum) {
        this.incomeSum = incomeSum;
    }

    public IPage<DistributionAwardGroupDayVO> getData() {
        return data;
    }

    public void setData(IPage<DistributionAwardGroupDayVO> data) {
        this.data = data;
    }
}
