package com.ydw.open.controller;

import com.ydw.open.model.db.TbBanner;
import com.ydw.open.service.ITbBannerService;
import com.ydw.open.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-06-09
 */
@Controller
@RequestMapping("/banner")
public class TbBannerController  extends BaseController{

    @Autowired
    private ITbBannerService iTbBannerService;



    @PostMapping(value = "/addBanner")
    @ResponseBody
    public ResultInfo addBanner(HttpServletRequest request, @RequestBody TbBanner tbBanner) {

        return iTbBannerService.addBanner(request,tbBanner);

    }

    @PostMapping(value = "/updateBanner")
    @ResponseBody
    public ResultInfo updateBanner(HttpServletRequest request, @RequestBody TbBanner tbBanner) {

        return iTbBannerService.updateBanner(request,tbBanner);

    }

    @GetMapping(value = "/getBanners")
    @ResponseBody
    public ResultInfo getBanners(HttpServletRequest request) {

        return iTbBannerService.getBanners(request);

    }
    @PostMapping(value = "/addBanners")
    @ResponseBody
    public ResultInfo addBanners(HttpServletRequest request, @RequestBody String  body) {

        return iTbBannerService.addBanners(request,body);

    }

    /**
     * 删除
     */
    @PostMapping(value = "/deleteBanners")
    @ResponseBody
    public ResultInfo deleteBanners(HttpServletRequest request, @RequestBody List<Integer> ids) {

        return iTbBannerService.deleteBanners(request,ids);

    }
}

