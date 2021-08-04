package com.ydw.recharge.model.db;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 优惠券卡
 * </p>
 *
 * @author xulh
 * @since 2021-04-23
 */
@TableName("tb_coupon_card")
public class CouponCard implements Serializable {

    /**
     * 主键
     */
    private String id;

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
     * 适用的充值卡
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
     * 有效开始时间
     */
    private LocalDateTime validBeginTime;

    /**
     * 有效期结束时间
     */
    private LocalDateTime validEndTime;

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

    public Integer getValidityType() {
        return validityType;
    }

    public void setValidityType(Integer validityType) {
        this.validityType = validityType;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Integer getUserDrawableNum() {
        return userDrawableNum;
    }

    public void setUserDrawableNum(Integer userDrawableNum) {
        this.userDrawableNum = userDrawableNum;
    }

    @Override
    public String toString() {
        return "CouponCard{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", promotionType=" + promotionType +
                ", rechargeCardId=" + rechargeCardId +
                ", obtainType=" + obtainType +
                ", validityType=" + validityType +
                ", drawableNum=" + drawableNum +
                ", drawedNum=" + drawedNum +
                ", userDrawableNum=" + userDrawableNum +
                ", discount=" + discount +
                ", reduction=" + reduction +
                ", duration=" + duration +
                ", validBeginTime=" + validBeginTime +
                ", validEndTime=" + validEndTime +
                ", valid=" + valid +
                '}';
    }
}
