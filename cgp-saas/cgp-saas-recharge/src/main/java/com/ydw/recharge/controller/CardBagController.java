package com.ydw.recharge.controller;


import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.service.ICardBagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * 用户卡包前端控制器
 * </p>
 *
 * @author xulh
 * @since 2020-10-07
 */
@RestController
@RequestMapping("/cardBag")
public class CardBagController extends BaseController{

    @Autowired
    private ICardBagService cardBagService;

    @GetMapping("/getUserUseableTime")
    public ResultInfo getUserUseableTime(@RequestParam(required = false) String userId){
        if (StringUtils.isBlank(userId)){
            userId = super.getAccount();
        }
        return cardBagService.getUserUseableTime(userId);
    }

    @PostMapping("/addCardBag")
    public ResultInfo addCardBag(@RequestBody HashMap<String, String> params){
        String userId = params.get("userId");
        String cardId = params.get("cardId");
        return cardBagService.createCardBag(userId,cardId);
    }

    @GetMapping("/getCardBagNum")
    public ResultInfo getCardBagNum(@RequestParam String userId, @RequestParam String cardId){
        return cardBagService.getCardBagNum(userId,cardId);
    }

    /**
     * 签到送时长接口（可配置参数）
     */
    @PostMapping("/sendDuration")
    public ResultInfo sendDuration(@RequestBody HashMap<String, String> params){
        String userId = params.get("userId");
        String time = params.get("time");
        return cardBagService.sendDuration(userId,time);
    }
}

