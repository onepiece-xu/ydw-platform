package com.ydw.recharge.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class DistributionAwardGroupDayVO implements Serializable {

    /**
     * 天
     */
    private String byday;

    /**
     * 贡献人数
     */
    private int contributionNum;

    /**
     * 贡献金额
     */
    private BigDecimal contributionSum;

    public String getByday() {
        return byday;
    }

    public void setByday(String byday) {
        this.byday = byday;
    }

    public int getContributionNum() {
        return contributionNum;
    }

    public void setContributionNum(int contributionNum) {
        this.contributionNum = contributionNum;
    }

    public BigDecimal getContributionSum() {
        return contributionSum;
    }

    public void setContributionSum(BigDecimal contributionSum) {
        this.contributionSum = contributionSum;
    }
}