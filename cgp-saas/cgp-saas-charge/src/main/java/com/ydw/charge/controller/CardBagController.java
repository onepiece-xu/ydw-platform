package com.ydw.charge.controller;


import com.ydw.charge.model.vo.ResultInfo;
import com.ydw.charge.service.ICardBagService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        long userUseableTime = cardBagService.getUserUseableTime(userId);
        return ResultInfo.success(userUseableTime);
    }

}

