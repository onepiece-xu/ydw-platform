package com.ydw.admin.model.db;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 分销奖励表
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
public class DistributionAward implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 收益方
     */
    private String income;

    /**
     * 贡献方
     */
    private String contribution;

    /**
     * 奖励事件（1：拉新，2：下级充值）
     */
    private String event;

    /**
     * 奖励金额
     */
    private BigDecimal amount;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 是否有效
     */
    private Boolean valid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getContribution() {
        return contribution;
    }

    public void setContribution(String contribution) {
        this.contribution = contribution;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "DistributionAward{" +
        "id=" + id +
        ", income=" + income +
        ", contribution=" + contribution +
        ", event=" + event +
        ", amount=" + amount +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", valid=" + valid +
        "}";
    }
}
