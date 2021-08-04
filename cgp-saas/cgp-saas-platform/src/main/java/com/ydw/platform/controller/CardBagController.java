package com.ydw.platform.controller;


import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.ICardBagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 * 用户资产汇总表 前端控制器
 * </p>
 *
 * @author hea
 * @since 2020-11-06
 */
@Controller
@RequestMapping("/cardBag")
public class CardBagController extends  BaseController{

    @Autowired
    private ICardBagService iCardBagService;

    /**
     * 卡包详情
     */
    @GetMapping("/getCardBag")
    public ResultInfo getCardBag(@RequestParam String userId){
        return  iCardBagService.getCardBag(userId);
    }
}

