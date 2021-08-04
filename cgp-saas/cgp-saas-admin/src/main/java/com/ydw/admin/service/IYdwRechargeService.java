package com.ydw.admin.service;

import com.ydw.admin.model.vo.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@FeignClient(value = "cgp-saas-recharge", path = "/cgp-saas-recharge")
public interface IYdwRechargeService {

    /**
     * 用户提现
     * @throws
     * @author xulh 2020/10/14 14:03
     **/
    @PostMapping("/withdrawRecord/userWithdraw")
    ResultInfo userWithdraw(@RequestBody HashMap<String,Object> param);
    /**
     * 获取补偿卡类别
     */
    @GetMapping("/rechargeCard/getRechargeCardsByType")
    ResultInfo getRechargeCardsByType(@RequestParam int type);

    /**
     * 给用户奖励时间
     */
    @PostMapping("/cardBag/addCardBag")
    ResultInfo addCardBag(@RequestBody HashMap<String, String> params);

}