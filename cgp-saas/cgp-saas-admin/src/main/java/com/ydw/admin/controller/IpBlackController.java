package com.ydw.admin.controller;


import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IIpBlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * ip黑名单 前端控制器
 * </p>
 *
 * @author xulh
 * @since 2021-05-10
 */
@RestController
@RequestMapping("/ipBlack")
public class IpBlackController extends BaseController{

    @Autowired
    private IIpBlackService ipBlackService;

    @GetMapping("/ipBlackList")
    public ResultInfo ipBlackList(@RequestParam(required = false) String search){
        return ipBlackService.getIpBlackList(search, buildPage());
    }

    @PostMapping("/addIpBlack")
    public ResultInfo addIpBlack(@RequestBody HashMap<String, String> params){
        String ip = params.get("ip");
        return ipBlackService.addIpBlack(ip);
    }

    @PostMapping("/delIpBlack")
    public ResultInfo delIpBlack(@RequestBody HashMap<String, Object> params){
        List<String> ids = (List<String>)params.get("ids");
        return ipBlackService.delIpBlack(ids);
    }
}

