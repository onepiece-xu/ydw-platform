package com.ydw.recharge.controller;

import com.ydw.recharge.model.vo.CouponCardBagVO;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.service.ICouponBagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 优惠券包 前端控制器
 * </p>
 *
 * @author xulh
 * @since 2021-04-22
 */
@RestController
@RequestMapping("/couponBag")
public class CouponBagController extends BaseController{

    @Autowired
    private ICouponBagService couponBagService;

    /**
     * 获取用户可用优惠券
     * @return
     */
    @GetMapping("/getUserUseableCoupon")
    public ResultInfo getUserUseableCoupon(@RequestParam(required = false) String rechargeCardId){
        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(1);
        List<CouponCardBagVO> result = couponBagService.getUserCouponBag(super.getAccount(), list, rechargeCardId);
        return ResultInfo.success(result);
    }

    /**
     * 获取用户已使用优惠券
     * @return
     */
    @GetMapping("/getUserInvalidCoupon")
    public ResultInfo getUserInvalidCoupon(){
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(3);
        List<CouponCardBagVO> result = couponBagService.getUserCouponBag(super.getAccount(), list);
        return ResultInfo.success(result);
    }

    /**
     * 获取用户可用优惠券
     * @return
     */
    @GetMapping("/getUserCouponBag")
    public ResultInfo getUserCouponBag(@RequestParam(required = false) Integer status){
        List<Integer> list = new ArrayList<>();
        if (status != null){
            list.add(status);
        }
        List<CouponCardBagVO> result = couponBagService.getUserCouponBag(super.getAccount(), list);
        return ResultInfo.success(result);
    }

    /**
     * 领取优惠券
     * @return
     */
    @PostMapping("/drawCoupon")
    public ResultInfo drawCoupon(@RequestBody HashMap<String, String> map){
        String userId = map.get("userId");
        if(!StringUtils.isNotBlank(userId)){
            userId = super.getAccount();
        }

        String couponId = map.get("id");
        //TODO 临时迁就前端
        String[] split = couponId.split(",");
        List<CouponCardBagVO> list = new ArrayList<>();
        for (String c : split){
            CouponCardBagVO couponCardBagVO = couponBagService.drawCoupon(userId, c);
            if (couponCardBagVO != null){
                list.add(couponCardBagVO);
            }
        }
        if (list.isEmpty()){
            return ResultInfo.fail("没有可领取的优惠券了！");
        }
        return ResultInfo.success();
    }

}

