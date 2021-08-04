package com.ydw.user.controller;


import com.ydw.user.model.db.TbKeyConfig;
import com.ydw.user.service.ITbKeyConfigService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class TbKeyConfigController extends BaseController {
    @Autowired
    private ITbKeyConfigService iTbKeyConfigService;

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
    public ResultInfo addConfig(HttpServletRequest request, @RequestBody TbKeyConfig tbKeyConfig){

        return iTbKeyConfigService.addConfig(request,tbKeyConfig);
    }

    @PostMapping(value = "/updateConfig")
    @ResponseBody
    public ResultInfo updateConfig(HttpServletRequest request, @RequestBody TbKeyConfig tbKeyConfig){

        return iTbKeyConfigService.updateConfig(request,tbKeyConfig);
    }

    @PostMapping(value = "/deleteConfig")
    @ResponseBody
    public ResultInfo deleteConfig(HttpServletRequest request, @RequestBody List<Integer> ids){
        return iTbKeyConfigService.deleteConfig(request,ids);
    }

}

