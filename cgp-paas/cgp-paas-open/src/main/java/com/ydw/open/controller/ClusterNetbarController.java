package com.ydw.open.controller;


import com.ydw.open.model.vo.ClusterNetbarBind;
import com.ydw.open.service.IClusterNetbarService;
import com.ydw.open.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-12-11
 */

@Controller
@RequestMapping("/clusterNetbar")
public class ClusterNetbarController extends BaseController{


    @Autowired
    private IClusterNetbarService iClusterNetbarService;

    @PostMapping("/bind")
    @ResponseBody
    public ResultInfo clusterNetbarBind (@RequestBody ClusterNetbarBind clusterNetbarBind ) {

        return iClusterNetbarService.clusterNetbarBind(clusterNetbarBind);
    }
}

