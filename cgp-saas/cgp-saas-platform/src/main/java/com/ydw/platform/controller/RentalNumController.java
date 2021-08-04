package com.ydw.platform.controller;

import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IDadagameService;
import com.ydw.platform.service.IPlayCardChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.TreeMap;

@RestController
@RequestMapping("/rentalNum")
public class RentalNumController extends BaseController{

    @Autowired
    private IDadagameService dadagameService;

    /**
     * 获取游戏账号
     * @return
     */
    @PostMapping("/dadagameReturnNumNotice")
    public ResultInfo dadagameReturnNumNotice(@RequestBody TreeMap<String, Object> params){
        if (dadagameService.checkSign(params)){
            dadagameService.returnNumNotice((String) params.get("startGameNo"));
            return ResultInfo.success();
        }else{
            return ResultInfo.fail("sign错误！");
        }
    }
}
