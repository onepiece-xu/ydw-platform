package com.ydw.user.controller;


import com.ydw.user.model.db.ClusterAppConfig;
import com.ydw.user.service.IClusterAppConfigService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2021-05-17
 */
@RestController
@RequestMapping("/clusterAppConfig")
public class ClusterAppConfigController {

    @Autowired
    private IClusterAppConfigService clusterAppConfigService;

    @GetMapping("/getAppClusterInfo")
    public ResultInfo getAppClusterInfo(@RequestParam String appId, @RequestParam String clusterId){
        return clusterAppConfigService.getAppClusterInfo(appId,clusterId);
    }

    @PostMapping("/bindAppCluster")
    public ResultInfo bindAppCluster(@RequestBody ClusterAppConfig clusterAppConfig){
        return clusterAppConfigService.bindAppCluster(clusterAppConfig);
    }
}

