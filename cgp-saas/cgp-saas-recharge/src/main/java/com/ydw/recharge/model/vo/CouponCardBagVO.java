package com.ydw.recharge.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ydw.recharge.model.db.CouponBag;
import com.ydw.recharge.model.db.CouponCard;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author xulh
 * @description: TODO
 * @date 2021/4/2310:22
 */
public class CouponCardBagVO implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 优惠券id
     */
    private String cardId;

    /**
     * 优惠券卡包id
     */
    private String bagId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 优惠类型（0：打折，1：抵扣）
     */
    private Integer promotionType;

    /**
     * 适用充值卡id
     */
    private String rechargeCardId;

    /**
     * 获取方式（0：RMB，1：签到，2：连续签到，3：拉新，4：注册， 5：节日活动）
     */
    private Integer obtainType;

    /**
     * 有效期类型(0：固定有效期，比如2021/05/01-2021/05/05；1：非固定有效期)
     */
    private Integer validityType;

    /**
     * 可领取张数(-1:无限次；其他张数)
     */
    private Integer drawableNum;

    /**
     * 已被领取的张数
     */
    private Integer drawedNum;

    /**
     * 单个用户可以领取的张数(-1:无限次；其他张数)
     */
    private Integer userDrawableNum;

    /**
     * 折扣
     */
    private BigDecimal discount;

    /**
     * 减免
     */
    private BigDecimal reduction;

    /**
     * 周期内有效的时长(比如：3表示领取优惠券后3天内有效，0则表示仅仅当日有效，-1则表示在有效期内都有效）
     */
    private Integer duration;

    /**
     * 状态 0：未使用，1：预使用，2：已使用，3：已过期
     */
    private Integer status;

    /**
     * 卡包领取时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createTime;

    /**
     * 卡包使用的时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime usedTime;

    /**
     * 卡包有效开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime validBeginTime;

    /**
     * 卡包有效期结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime validEndTime;

    /**
     * 剩余时长（小时）
     */
    private Long surplusTime;

    public CouponCardBagVO() {
    }

    public CouponCardBagVO(CouponCard couponCard, CouponBag couponBag){
        this.bagId = couponBag.getId();
        this.cardId = couponCard.getId();
        this.createTime = couponBag.getCreateTime();
        this.description = couponCard.getDescription();
        this.discount = couponCard.getDiscount();
        this.drawableNum = couponCard.getDrawableNum();
        this.drawedNum = couponCard.getDrawedNum();
        this.userDrawableNum = couponCard.getUserDrawableNum();
        this.name = couponCard.getName();
        this.obtainType = couponCard.getObtainType();
        this.rechargeCardId = couponCard.getRechargeCardId();
        this.reduction = couponCard.getReduction();
        this.promotionType = couponCard.getPromotionType();
        this.status = couponBag.getStatus();
        this.userId = couponBag.getUserId();
        this.usedTime = couponBag.getUsedTime();
        this.validBeginTime = couponBag.getValidBeginTime();
        this.validEndTime = couponBag.getValidEndTime();
        this.duration = couponCard.getDuration();
        this.validityType = couponCard.getValidityType();
        this.surplusTime = validEndTime == null ? null : Duration.between(LocalDateTime.now(), this.validEndTime).toHours();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getBagId() {
        return bagId;
    }

    public void setBagId(String bagId) {
        this.bagId = bagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(Integer promotionType) {
        this.promotionType = promotionType;
    }

    public String getRechargeCardId() {
        return rechargeCardId;
    }

    public void setRechargeCardId(String rechargeCardId) {
        this.rechargeCardId = rechargeCardId;
    }

    public Integer getObtainType() {
        return obtainType;
    }

    public void setObtainType(Integer obtainType) {
        this.obtainType = obtainType;
    }

    public Integer getDrawableNum() {
        return drawableNum;
    }

    public void setDrawableNum(Integer drawableNum) {
        this.drawableNum = drawableNum;
    }

    public Integer getDrawedNum() {
        return drawedNum;
    }

    public void setDrawedNum(Integer drawedNum) {
        this.drawedNum = drawedNum;
    }

    public Integer getUserDrawableNum() {
        return userDrawableNum;
    }

    public void setUserDrawableNum(Integer userDrawableNum) {
        this.userDrawableNum = userDrawableNum;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getReduction() {
        return reduction;
    }

    public void setReduction(BigDecimal reduction) {
        this.reduction = reduction;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(LocalDateTime usedTime) {
        this.usedTime = usedTime;
    }

    public LocalDateTime getValidBeginTime() {
        return validBeginTime;
    }

    public void setValidBeginTime(LocalDateTime validBeginTime) {
        this.validBeginTime = validBeginTime;
    }

    public LocalDateTime getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(LocalDateTime validEndTime) {
        this.validEndTime = validEndTime;
    }

    public Integer getValidityType() {
        return validityType;
    }

    public void setValidityType(Integer validityType) {
        this.validityType = validityType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getSurplusTime() {
        return validEndTime == null ? null : Duration.between(LocalDateTime.now(), this.validEndTime).toHours();
    }

    public void setSurplusTime(Long surplusTime) {
        this.surplusTime = surplusTime;
    }
}
