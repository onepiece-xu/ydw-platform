package com.ydw.admin.controller;


import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.model.vo.VirtualkeyConfigVO;
import com.ydw.admin.service.IVirtualkeyConfigService;
import com.ydw.admin.service.IVirtualkeyRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private IVirtualkeyRelationService virtualkeyRelationService;

    @PostMapping("/uploadVirtualkeyConfig")
    public ResultInfo uploadVirtualkeyConfig(@RequestBody VirtualkeyConfigVO vo){
        vo.setUserId("admin");
        vo.setCreateType(0);
        return virtualkeyConfigService.uploadVirtualkeyConfig(vo);
    }

    @PostMapping("/updateVirtualkeyConfig")
    public ResultInfo updateVirtualkeyConfig(@RequestBody VirtualkeyConfigVO vo){
        return virtualkeyConfigService.updateVirtualkeyConfig(vo);
    }

    @PostMapping("/delVirtualkeyConfig")
    public ResultInfo delVirtualkeyConfig(@RequestBody List<String> configIds){
        return virtualkeyConfigService.delVirtualkeyConfig(configIds);
    }

    @GetMapping("/getVirtualkeyConfigs")
    public ResultInfo getVirtualkeyConfigs(@RequestParam(required = false) String search){
        return virtualkeyConfigService.getVirtualkeyConfigs(buildPage(), search);
    }

    @GetMapping("/getBindedApp")
    public ResultInfo getBindedApp(@RequestParam String id, @RequestParam(required = false) String search){
        return virtualkeyRelationService.getBindedApp(super.buildPage(), id,search);
    }

    @GetMapping("/getUnBindApp")
    public ResultInfo getUnBindApp(@RequestParam String id, @RequestParam(required = false) String search){
        return virtualkeyRelationService.getUnBindApp(super.buildPage(),id,search);
    }

    @PostMapping("/bindApp")
    public ResultInfo bindApp(@RequestBody VirtualkeyConfigVO vo){
        String configId = vo.getId();
        String[] appIds = vo.getAppIds();
        return virtualkeyRelationService.bindApp(configId, appIds);
    }

    @PostMapping("/unbindApp")
    public ResultInfo unbindApp(@RequestBody VirtualkeyConfigVO vo){
        String configId = vo.getId();
        String[] appIds = vo.getAppIds();
        return virtualkeyRelationService.unbindApp(configId, appIds);
    }
}

