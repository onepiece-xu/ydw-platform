package com.ydw.admin.model.db;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户资产汇总表
 * </p>
 *
 * @author xulh
 * @since 2020-10-07
 */
@TableName("tb_card_bag")
public class CardBag implements Serializable {

    private static final long serialVersionUID=1L;

    private String id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 充值卡id
     */
    private String cardId;

    /**
     * 会员总时长（分钟）
     */
    private Integer duration;

    /**
     * 已使用时长（分钟）
     */
    private Integer usedDuration;

    /**
     * 会员卡开始时间
     */
    private LocalDateTime beginTime;

    /**
     * 会员卡过期时间
     */
    private LocalDateTime endTime;

    /**
     * 用户会员卡创建时间
     */
    private LocalDateTime createTime;

    /**
     * 用户会员卡修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 时段卡特有，时段开始时间（比如23:00）
     */
    private String beginPeriod;

    /**
     * 时段卡特有，时段结束时间（比如07:00）
     */
    private String endPeriod;

    /**
     * 是否有效(0:失效，1：有效)用完或者过期即无效
     */
    private Boolean valid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getUsedDuration() {
        return usedDuration;
    }

    public void setUsedDuration(Integer usedDuration) {
        this.usedDuration = usedDuration;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
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

    @Override
    public String toString() {
        return "CardBag{" +
        "id=" + id +
        ", userId=" + userId +
        ", cardId=" + cardId +
        ", duration=" + duration +
        ", usedDuration=" + usedDuration +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", valid=" + valid +
        "}";
    }
}
