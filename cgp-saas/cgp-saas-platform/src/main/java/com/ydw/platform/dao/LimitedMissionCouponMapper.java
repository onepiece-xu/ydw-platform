package com.ydw.platform.dao;

import com.ydw.platform.model.db.CouponCard;
import com.ydw.platform.model.db.LimitedMissionCoupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2021-07-07
 */
public interface LimitedMissionCouponMapper extends BaseMapper<LimitedMissionCoupon> {

    List<CouponCard> getCouponList(String id);
}
