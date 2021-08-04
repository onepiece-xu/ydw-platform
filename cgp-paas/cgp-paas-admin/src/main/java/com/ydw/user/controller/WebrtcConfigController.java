package com.ydw.user.controller;


import com.ydw.user.model.db.TbWebrtcConfig;
import com.ydw.user.service.IWebrtcConfigService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-08-07
 */
@Controller
@RequestMapping("/webrtcConfig")
public class     WebrtcConfigController extends  BaseController{
    @Autowired
    private IWebrtcConfigService iWebrtcConfigService;

    @GetMapping(value = "/getConfigList")
    @ResponseBody
    public ResultInfo getConfigList(@RequestParam(required = false)String body){
        return  iWebrtcConfigService.getConfigList(body,buildPage());
    }
    @PostMapping(value = "/addConfig")
    @ResponseBody
    public ResultInfo addConfig(@RequestBody TbWebrtcConfig webrtcConfig){
        return  iWebrtcConfigService.addConfig(webrtcConfig);
    }

    @PostMapping(value = "/updateConfig")
    @ResponseBody
    public  ResultInfo updateConfig(@RequestBody TbWebrtcConfig webrtcConfig){
        return iWebrtcConfigService.updateConfig(webrtcConfig);
    }

    @PostMapping(value = "/deleteConfig")
    @ResponseBody
    public  ResultInfo deleteConfig(@RequestBody List<Integer> ids){
        return iWebrtcConfigService.deleteConfig(ids);
    }


}

