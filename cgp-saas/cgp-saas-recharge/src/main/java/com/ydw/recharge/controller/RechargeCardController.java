package com.ydw.recharge.controller;


import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.service.IRechargeCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 充值卡 前端控制器
 * </p>
 *
 * @author xulh
 * @since 2020-10-07
 */
@RestController
@RequestMapping("/rechargeCard")
public class RechargeCardController {

    @Autowired
    private IRechargeCardService rechargeCardService;

    @GetMapping("/getRechargeCards")
    public ResultInfo getRechargeCards(){
        return rechargeCardService.getRechargeCards();
    }

    @GetMapping("/getPlayRechargeCards")
    public ResultInfo getPlayRechargeCards(){
        return rechargeCardService.getPlayRechargeCards();
    }

    @GetMapping("/getComboRechargeCards")
    public ResultInfo getComboRechargeCards(){
        return rechargeCardService.getComboRechargeCards();
    }

    @GetMapping("/getRechargeCardsByType")
    public ResultInfo getRechargeCardsByType(@RequestParam int type){
        return rechargeCardService.getRechargeCardsByType(type);
    }
}

