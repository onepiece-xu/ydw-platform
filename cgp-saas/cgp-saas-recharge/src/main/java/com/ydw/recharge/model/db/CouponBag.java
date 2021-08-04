package com.ydw.recharge.model.db;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 优惠券包
 * </p>
 *
 * @author xulh
 * @since 2021-04-22
 */
@TableName("tb_coupon_bag")
public class CouponBag implements Serializable {

    private String id;

    /**
     * 优惠券id
     */
    private String couponId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 0：未使用，1：预使用，2：已使用，3：已过期
     */
    private Integer status;

    /**
     * 使用凭证
     */
    private String ticket;

    /**
     * 领取时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime createTime;

    /**
     * 使用时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime usedTime;

    /**
     * 有效期内开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime validBeginTime;

    /**
     * 有效期内结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime validEndTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "CouponBag{" +
                "id='" + id + '\'' +
                ", couponId='" + couponId + '\'' +
                ", userId='" + userId + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", usedTime=" + usedTime +
                ", validBeginTime=" + validBeginTime +
                ", validEndTime=" + validEndTime +
                '}';
    }
}
