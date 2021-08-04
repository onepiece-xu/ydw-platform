package com.ydw.platform.controller;


import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IActivityCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xulh
 * @since 2021-05-27
 */
@Controller
@RequestMapping("/activityCenter")
public class ActivityCenterController extends  BaseController {

    @Autowired
    private IActivityCenterService iActivityCenterService;

    /**
     * 获取活动列表
     */
    @GetMapping("/getActivityList")
    public ResultInfo getActivityList(HttpServletRequest request) {
        return iActivityCenterService.getActivityList(buildPage());
    }

    /**
     * 获取活动优惠券信息
     */
    @GetMapping("/getActivityCoupon")
    public ResultInfo getActivityList(@RequestParam Integer id) {
        return iActivityCenterService.getActivityCoupon(id);
    }

}
