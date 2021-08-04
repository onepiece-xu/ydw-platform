package com.ydw.recharge.controller;

import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.service.IDistributionAwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/2413:49
 */
@RestController
@RequestMapping("/distributionAward")
public class DistributionAwardController extends BaseController{

    @Autowired
    private IDistributionAwardService distributionAwardService;


    /**
     * 用户收益列表
     * @return
     */
    @GetMapping("/getOwnerDistributionAward")
    public ResultInfo getOwnerDistributionAward(){
        String userId = super.getAccount();
        return distributionAwardService.getUserDistributionAward(super.buildPage(),userId);
    }
}
