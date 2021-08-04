package com.ydw.platform.controller;


import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IDistributionAwardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * 分销奖励表 前端控制器
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
@RestController
@RequestMapping("/distributionAward")
public class DistributionAwardController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IDistributionAwardService distributionAwardService;

    @PostMapping("/pullNewAward")
    public ResultInfo pullNewAward(@RequestBody HashMap<String, String> map){
        String recommender = map.get("recommender");
        String inferior = map.get("inferior");
        return distributionAwardService.pullNewAward(recommender, inferior);
    }

    @GetMapping("/registerAward")
    public ResultInfo registerAward(@RequestParam String userId){
        return distributionAwardService.registerAward(userId);
    }

}

