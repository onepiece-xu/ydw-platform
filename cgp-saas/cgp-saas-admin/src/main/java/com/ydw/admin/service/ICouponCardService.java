package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.CouponCard;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.vo.ResultInfo;

import java.util.List;

/**
 * <p>
 * 优惠券卡 服务类
 * </p>
 *
 * @author xulh
 * @since 2021-04-26
 */
public interface ICouponCardService extends IService<CouponCard> {

    ResultInfo getCouponList(String search, Page buildPage);

    ResultInfo addCoupon(CouponCard couponCard);

    ResultInfo updateCoupon(CouponCard couponCard);

    ResultInfo available(List ids, int type);
}
