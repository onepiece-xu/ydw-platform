package com.ydw.recharge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.recharge.model.db.CouponCard;
import com.ydw.recharge.model.vo.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-04-22
 */
public interface ICouponCardService extends IService<CouponCard> {

    ResultInfo getDrawableCoupon(String userId);
}
