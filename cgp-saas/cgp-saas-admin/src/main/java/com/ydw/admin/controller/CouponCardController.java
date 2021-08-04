package com.ydw.admin.controller;


import com.ydw.admin.model.db.CouponCard;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.ICouponCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 优惠券卡 前端控制器
 * </p>
 *
 * @author xulh
 * @since 2021-04-26
 */
@RestController
@RequestMapping("/couponCard")
public class CouponCardController extends BaseController{

    @Autowired
    private ICouponCardService couponCardService;

    @GetMapping("/getCouponList")
    public ResultInfo getCouponList(@RequestParam(required = false) String search){
        return couponCardService.getCouponList(search, buildPage());
    }

    @PostMapping("/addCoupon")
    public ResultInfo addCoupon(@RequestBody CouponCard couponCard){
        return couponCardService.addCoupon(couponCard);
    }

    @PostMapping("/updateCoupon")
    public ResultInfo updateCoupon(@RequestBody CouponCard couponCard){
        return couponCardService.updateCoupon(couponCard);
    }

    @PostMapping("/available")
    public ResultInfo available(@RequestBody HashMap<String, Object> param){
        int type = (int)param.get("type");
        List ids = (List)param.get("ids");
        return couponCardService.available(ids, type);
    }
}

