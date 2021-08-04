package com.ydw.admin.controller;

import com.ydw.admin.model.db.Banner;
import com.ydw.admin.model.db.BannerType;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IBannerService;
import com.ydw.admin.service.IBannerTypeService;
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


    /**
     * 类型
     * @param bannerType
     * @return
     */
    @PostMapping(value = "/addBannerType")
    @ResponseBody
    public ResultInfo addBannerType(@RequestBody BannerType bannerType) {

        return iTbBannerService.addBannerType(bannerType);

    }
    /**
     * 类型
     * @return
     */
    @GetMapping(value = "/getBannerTypes")
    @ResponseBody
    public ResultInfo getBannerTypes() {

        return iTbBannerService.getBannerTypes();

    }
    /**
     * 类型
     * @param bannerType
     * @return
     */
    @PostMapping(value = "/updateBannerType")
    @ResponseBody
    public ResultInfo updateBannerType(@RequestBody BannerType bannerType) {

        return iTbBannerService.updateBannerType(bannerType);

    }

    /**
     * 类型
     * @return
     */
    @PostMapping(value = "/deleteBannerType")
    @ResponseBody
    public ResultInfo deleteBannerType(@RequestBody List<Integer> ids) {

        return iTbBannerService.deleteBannerType(ids);

    }




    @PostMapping(value = "/addBanner")
    @ResponseBody
    public ResultInfo addBanner(HttpServletRequest request, Banner tbBanner) {

        return iTbBannerService.addBanner(request,tbBanner);

    }

    @PostMapping(value = "/updateBanner")
    @ResponseBody
    public ResultInfo updateBanner(HttpServletRequest request,  Banner tbBanner) {

        return iTbBannerService.updateBanner(request,tbBanner);

    }

    @GetMapping(value = "/getBanners")
    @ResponseBody
    public ResultInfo getBanners(HttpServletRequest request,@RequestParam (required = false)Integer type) {

        return iTbBannerService.getBanners(request,type,buildPage());

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

