package com.ydw.recharge.model.db;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 充值卡
 * </p>
 *
 * @author xulh
 * @since 2020-10-13
 */
@TableName("tb_recharge_card")
public class RechargeCard implements Serializable {

    /**
     * 主键
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 预计充值费用（RMB）
     */
    private BigDecimal expectCost;

    /**
     * 实际充值费用（RMB）
     */
    private BigDecimal finalCost;

    /**
     * 描述
     */
    private String description;

    /**
     * 描述1(后期去掉)
     */
    private String description1;

    /**
     * 类型（1：周期卡，一段时间内有效，
     *      2：时长卡，永久有效
     *      3：时段卡，某个特定时间段有效，比如包夜卡晚11点到早7点），一段时间内有效
     *      4：畅玩卡，跟游戏租号相关
     *      5：套餐卡，多张卡的集合优惠相关
     */
    private Integer type;

    /**
     * 获取方式（0：RMB，1：活动）
     */
    private Integer obtainType;

    /**
     * 可用时长（小时）
     */
    private Integer duration;

    /**
     * 有效（天）期（-1:一直有效 0：当天有效，其他就是失效天数）
     */
    private Integer validityTime;

    /**
     * 时段卡特有，时段开始时间（比如23:00）
     */
    private String beginPeriod;

    /**
     * 时段卡特有，时段结束时间（比如07:00）
     */
    private String endPeriod;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否有效(0:失效，1：有效)
     */
    private Boolean valid;

    /**
     * 套餐卡中卡的集合以，分割
     */
    private String comboCardIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getObtainType() {
        return obtainType;
    }

    public void setObtainType(Integer obtainType) {
        this.obtainType = obtainType;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(Integer validityTime) {
        this.validityTime = validityTime;
    }

    public String getBeginPeriod() {
        return beginPeriod;
    }

    public void setBeginPeriod(String beginPeriod) {
        this.beginPeriod = beginPeriod;
    }

    public String getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(String endPeriod) {
        this.endPeriod = endPeriod;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getExpectCost() {
        return expectCost;
    }

    public void setExpectCost(BigDecimal expectCost) {
        this.expectCost = expectCost;
    }

    public BigDecimal getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(BigDecimal finalCost) {
        this.finalCost = finalCost;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getComboCardIds() {
        return comboCardIds;
    }

    public void setComboCardIds(String comboCardIds) {
        this.comboCardIds = comboCardIds;
    }

    @Override
    public String toString() {
        return "RechargeCard{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", expectCost=" + expectCost +
                ", finalCost=" + finalCost +
                ", description='" + description + '\'' +
                ", description1='" + description1 + '\'' +
                ", type=" + type +
                ", obtainType=" + obtainType +
                ", duration=" + duration +
                ", validityTime=" + validityTime +
                ", beginPeriod='" + beginPeriod + '\'' +
                ", endPeriod='" + endPeriod + '\'' +
                ", createTime=" + createTime +
                ", valid=" + valid +
                ", comboCardIds='" + comboCardIds + '\'' +
                '}';
    }
}
