package com.ydw.admin.model.vo;

import com.ydw.admin.model.db.ActivityCenter;

public class ActivityCenterVO extends ActivityCenter {
    private String couponId;
    private  String couponName;

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }
}
