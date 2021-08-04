package com.ydw.platform.service;

import com.ydw.platform.model.vo.ResultInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@FeignClient(value = "cgp-saas-recharge", path = "/cgp-saas-recharge")
public interface IYdwRechargeService {

    /**
     * 用户签到充值
     * @param userId
     * @throws
     * @author xulh 2020/10/14 14:03
     **/
    @PostMapping("/recharge/signRecharge")
    String signRecharge(@RequestParam(value = "userId") String userId);

    /**
     * 用户添加卡包
     * @throws
     * @author xulh 2020/10/14 14:03
     **/
    @PostMapping("/cardBag/addCardBag")
    String addCardBag(@RequestBody HashMap<String, String> params);

    /**
     * 用户签到充值
     * @param userId
     * @throws
     * @author xulh 2020/10/14 14:03
     **/
    @GetMapping("/cardBag/getCardBagNum")
    ResultInfo getCardBagNum(@RequestParam(value = "userId") String userId, @RequestParam(value = "cardId") String cardId);

    /**
     * 连续签到
     * @param userId
     * @return
     */
    @PostMapping("/recharge/continueSignRecharge")
    String continueSignRecharge(@RequestParam(value = "userId") String userId);

    /**
     *领优惠券
     */
    @PostMapping("/couponBag/drawCoupon")
    String drawCoupon(@RequestBody HashMap<String, String> params);

    /**
     *签到送时间
     */
    @PostMapping("/cardBag/sendDuration")
    String sendDuration(@RequestBody HashMap<String, String> params);
}
