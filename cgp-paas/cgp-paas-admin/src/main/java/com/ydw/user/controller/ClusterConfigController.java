package com.ydw.user.controller;


import com.ydw.user.service.IClusterConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-08-07
 */
@Controller
@RequestMapping("/clusterConfig")
public class ClusterConfigController {
    @Autowired
    private IClusterConfigService iTbClusterConfigService;
}

