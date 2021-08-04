package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.ActivityCoupon;
import com.ydw.admin.model.db.CouponCard;

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

    Page<CouponCard> getBindCoupon(Integer id, Page buildPage);

    Page<CouponCard> getUnbindCoupon(Integer id, Page buildPage);
}
