package com.ydw.recharge.model.db;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 充值记录表
 * </p>
 *
 * @author xulh
 * @since 2020-10-07
 */
@TableName("tb_recharge")
public class Recharge implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 主题
     */
    private String subject;

    /**
     * 费用（RMB）
     */
    private BigDecimal cost;

    /**
     * 实际费用（RMB）
     */
    private BigDecimal finalCost;

    /**
     * 用户id
     */
    private String account;

    /**
     * 支付类型（1：支付宝，2：微信）
     */
    private Integer payType;

    /**
     * 充值卡id
     */
    private String cardId;

    /**
     * 订单状态(0:未支付，1:已支付，2:取消支付，3:支付失败)
     */
    private Integer status;

    /**
     * 订单创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 优惠券id
     */
    private String couponBagId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getCouponBagId() {
        return couponBagId;
    }

    public void setCouponBagId(String couponBagId) {
        this.couponBagId = couponBagId;
    }

    public BigDecimal getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(BigDecimal finalCost) {
        this.finalCost = finalCost;
    }

    @Override
    public String toString() {
        return "Recharge{" +
        "id=" + id +
        ", orderNum=" + orderNum +
        ", subject=" + subject +
        ", cost=" + cost +
        ", account=" + account +
        ", payType=" + payType +
        ", cardId=" + cardId +
        ", status=" + status +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
