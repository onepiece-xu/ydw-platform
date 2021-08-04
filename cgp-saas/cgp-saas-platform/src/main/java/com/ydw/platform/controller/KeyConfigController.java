package com.ydw.platform.controller;


import com.ydw.platform.model.db.KeyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IKeyConfigService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-07-27
 */
@Controller
@RequestMapping("/keyConfig")
public class KeyConfigController extends BaseController {
    @Autowired
    private IKeyConfigService iTbKeyConfigService;

    /**
     * 根据条件查询虚拟键位列表
     * @param request
     * @param body
     * @return
     */
    @PostMapping(value = "/getConfigList")
    @ResponseBody
    public ResultInfo getConfigList(HttpServletRequest request, @RequestBody String body){

        return iTbKeyConfigService.getConfigList(request,body,buildPage());
    }

    @PostMapping(value = "/addConfig")
    @ResponseBody
    public ResultInfo addConfig(HttpServletRequest request, @RequestBody KeyConfig tbKeyConfig){

        return iTbKeyConfigService.addConfig(request,tbKeyConfig);
    }

    @PostMapping(value = "/updateConfig")
    @ResponseBody
    public ResultInfo updateConfig(HttpServletRequest request, @RequestBody KeyConfig tbKeyConfig){

        return iTbKeyConfigService.updateConfig(request,tbKeyConfig);
    }

    @PostMapping(value = "/deleteConfig")
    @ResponseBody
    public ResultInfo deleteConfig(HttpServletRequest request, @RequestBody List<Integer> ids){
        return iTbKeyConfigService.deleteConfig(request,ids);
    }

}

