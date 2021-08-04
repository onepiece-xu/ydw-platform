package com.ydw.platform.model.vo;

import com.ydw.platform.model.db.CouponCard;
import com.ydw.platform.model.db.TimeLimitedMission;

import java.sql.Time;
import java.util.List;

public class TimeLimitedMissionVO  extends TimeLimitedMission {

    private List<CouponCard> couponCardList;

    public List<CouponCard> getCouponCardList() {
        return couponCardList;
    }

    public void setCouponCardList(List<CouponCard> couponCardList) {
        this.couponCardList = couponCardList;
    }
}
