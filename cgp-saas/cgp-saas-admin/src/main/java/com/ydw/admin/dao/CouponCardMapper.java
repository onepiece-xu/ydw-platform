package com.ydw.admin.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.CouponCard;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 优惠券卡 Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2021-04-26
 */
public interface CouponCardMapper extends BaseMapper<CouponCard> {

    Page getCouponList(String search, Page buildPage);

}
