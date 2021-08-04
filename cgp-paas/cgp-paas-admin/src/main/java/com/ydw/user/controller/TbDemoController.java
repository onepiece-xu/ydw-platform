package com.ydw.user.controller;



import com.ydw.user.model.db.TbBanner;
import com.ydw.user.model.db.TbDemo;
import com.ydw.user.service.ITbBannerService;
import com.ydw.user.service.ITbDemoService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-06-09
 */
@Controller
@RequestMapping("/demo")
public class TbDemoController  extends BaseController{
    @Autowired
    private ITbDemoService iTbDemoService;
    @Autowired
    private ITbBannerService ITbBannerService;


    @GetMapping(value = "/getAppsList")
    @ResponseBody
    public ResultInfo getAppsList(HttpServletRequest request) {

        return iTbDemoService.getAppsList(request);

    }

    @PostMapping(value = "/getDemoAppsListPc")
    @ResponseBody
    public ResultInfo getDemoAppsListPc(HttpServletRequest request,@RequestBody TbDemo demo) {

        return iTbDemoService.getDemoAppsListPc(request,demo);

    }

    /**
     * SDK 仅展示sdk类型列表
     * @param request
     * @return
     */
    @PostMapping(value = "/getDemoAppsList")
    @ResponseBody
    public ResultInfo getDemoAppsList(HttpServletRequest request) {

        return iTbDemoService.getDemoAppsList(request);

    }

    @PostMapping(value = "/commit")
    @ResponseBody
    public ResultInfo commit(HttpServletRequest request, @RequestBody String body) {

        return iTbDemoService.commit(request,body);

    }

    @PostMapping(value = "/addBanner")
    @ResponseBody
    public ResultInfo addBanner(HttpServletRequest request, @RequestBody TbBanner tbBanner) {

        return ITbBannerService.addBanner(request,tbBanner);

    }

    @PostMapping(value = "/updateBanner")
    @ResponseBody
    public ResultInfo updateBanner(HttpServletRequest request, @RequestBody TbBanner tbBanner) {

        return ITbBannerService.updateBanner(request,tbBanner);

    }

    @GetMapping(value = "/getBanners")
    @ResponseBody
    public ResultInfo getBanners(HttpServletRequest request) {

        return ITbBannerService.getBanners(request);

    }
    @PostMapping(value = "/addBanners")
    @ResponseBody
    public ResultInfo addBanners(HttpServletRequest request, @RequestBody String  body) {

        return ITbBannerService.addBanners(request,body);

    }
}

