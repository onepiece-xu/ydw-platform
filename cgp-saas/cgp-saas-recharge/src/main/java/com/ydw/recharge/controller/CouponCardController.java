package com.ydw.recharge.controller;

import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.service.ICouponCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  优惠券卡控制器
 * </p>
 *
 * @author xulh
 * @since 2021-04-22
 */
@RestController
@RequestMapping("/couponCard")
public class CouponCardController extends BaseController{

    @Autowired
    private ICouponCardService couponCardService;

    /**
     * 获取用户可领取的优惠券
     * @return
     */
    @GetMapping("/getDrawableCoupon")
    public ResultInfo getDrawableCoupon(){
        return couponCardService.getDrawableCoupon(super.getAccount());
    }
}

