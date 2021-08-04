package com.ydw.platform.controller;

import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.model.vo.VirtualkeyConfigVO;
import com.ydw.platform.service.IVirtualkeyConfigService;
import com.ydw.platform.service.IVirtualkeyUsedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xulh
 * @since 2021-01-13
 */
@RestController
@RequestMapping("/virtualkeyConfig")
public class VirtualkeyConfigController extends BaseController{

    @Autowired
    private IVirtualkeyConfigService virtualkeyConfigService;

    @Autowired
    private IVirtualkeyUsedService virtualkeyUsedService;

    @PostMapping("/uploadVirtualkeyConfig")
    public ResultInfo uploadVirtualkeyConfig(@RequestBody VirtualkeyConfigVO vo){
        vo.setUserId(super.getAccount());
        return virtualkeyConfigService.uploadVirtualkeyConfig(vo);
    }

    @PostMapping("/updateVirtualkeyConfig")
    public ResultInfo updateVirtualkeyConfig(@RequestBody VirtualkeyConfigVO vo){
        vo.setUserId(super.getAccount());
        return virtualkeyConfigService.updateVirtualkeyConfig(vo);
    }

    @PostMapping("/delVirtualkeyConfig")
    public ResultInfo delVirtualkeyConfig(@RequestBody HashMap<String, String> param){
        String configId = param.get("configId");
        String userId = super.getAccount();
        return virtualkeyConfigService.delVirtualkeyConfig(configId, userId);
    }

    @GetMapping("/getVirtualkeyConfigs")
    public ResultInfo getVirtualkeyConfigs(@RequestParam String appId,@RequestParam int keyType){
        String userId = super.getAccount();
        return virtualkeyConfigService.getVirtualkeyConfigs(userId, appId, keyType, super.buildPage());
    }

    @PostMapping("/applyVirtualkeyConfig")
    public ResultInfo applyVirtualkeyConfig(@RequestBody HashMap<String,String> param){
        String appId = param.get("appId");
        String configId = param.get("configId");
        String userId = super.getAccount();
        return virtualkeyUsedService.applyVirtualkeyConfig(configId, appId, userId);
    }
}

