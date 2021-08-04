package com.ydw.platform.controller;
import com.ydw.platform.model.db.Banner;

import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IBannerService;

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
public class BannerController extends BaseController {

    @Autowired
    private IBannerService iTbBannerService;


    @GetMapping(value = "/getBanners")
    @ResponseBody
    public ResultInfo getBanners(HttpServletRequest request,@RequestParam (required = false)Integer type
            ,@RequestParam (required = false)Integer platform) {

        return iTbBannerService.getBanners(request,type,platform,buildPage());

    }
}

