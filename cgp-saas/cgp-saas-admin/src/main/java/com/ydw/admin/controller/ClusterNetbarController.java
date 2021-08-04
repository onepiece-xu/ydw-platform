package com.ydw.admin.controller;


import com.ydw.admin.model.vo.ClusterNetbarBind;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IClusterNetbarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

