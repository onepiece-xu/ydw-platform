package com.ydw.platform.model.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xulh
 * @since 2021-07-07
 */
public class LimitedMissionCoupon implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 限时任务id
     */
    private String missionId;

    /**
     * 优惠券id
     */
    private String couponId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMissionId() {
        return missionId;
    }

    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    @Override
    public String toString() {
        return "LimitedMissionCoupon{" +
        "id=" + id +
        ", missionId=" + missionId +
        ", couponId=" + couponId +
        "}";
    }
}
