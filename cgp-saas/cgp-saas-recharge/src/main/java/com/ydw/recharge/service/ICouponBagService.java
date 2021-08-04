package com.ydw.recharge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.recharge.model.db.CouponBag;
import com.ydw.recharge.model.db.RechargeCard;
import com.ydw.recharge.model.vo.CouponCardBagVO;

import java.util.List;

/**
 * <p>
 * 优惠券包 服务类
 * </p>
 *
 * @author xulh
 * @since 2021-04-22
 */
public interface ICouponBagService extends IService<CouponBag> {

    CouponCardBagVO drawCoupon(String userId, String couponId);

    CouponCardBagVO preConsumeCouponBag(String rechargeCardId, String couponBagId);

    boolean consumeCouponBag(String couponBagId);

    boolean releaseCouponBag(String couponBagId);

    List<CouponCardBagVO> getUserCouponBag(String account, List<Integer> status);

    List<CouponCardBagVO> getUserCouponBag(String account, List<Integer> list, String rechargeCardId);

}
