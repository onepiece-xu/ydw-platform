package com.ydw.platform.dao;

import com.ydw.platform.model.db.ActivityCoupon;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.platform.model.db.CouponCard;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2021-05-27
 */
public interface ActivityCouponMapper extends BaseMapper<ActivityCoupon> {

    List<CouponCard> getCouponByActivityId(Integer id);
}
