package com.ydw.platform.controller;


import com.ydw.platform.model.db.BitRate;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IBitRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/** 码率API
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hea
 * @since 2020-10-07
 */
@Controller
@RequestMapping("/bitRate")
public class BitRateController  extends  BaseController{
    @Autowired
    private IBitRateService iBitRateService;

    @GetMapping("/getList")
    public ResultInfo getList(@RequestParam Integer type){
        return  iBitRateService.getList(type,buildPage());
    }

    @PostMapping("/addBitRate")
    public  ResultInfo addBitRate(@RequestBody BitRate bitRate){
        return  iBitRateService.addBitRate(bitRate);
    }

    @PostMapping("/updateBitRate")
    public  ResultInfo updateBitRate(@RequestBody BitRate bitRate){
        return  iBitRateService.updateBitRate(bitRate);
    }
    @PostMapping("/deleteBitRate")
    public  ResultInfo deleteBitRate(@RequestBody List<Integer> ids){
        return  iBitRateService.deleteBitRate(ids);
    }
}

