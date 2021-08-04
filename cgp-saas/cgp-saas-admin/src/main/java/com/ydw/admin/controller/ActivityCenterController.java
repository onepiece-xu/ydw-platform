package com.ydw.admin.controller;


import com.ydw.admin.model.db.ActivityCenter;
import com.ydw.admin.model.vo.ActivityCenterVO;
import com.ydw.admin.model.vo.FixActivityVO;
import com.ydw.admin.model.vo.FixBindCoupon;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IActivityCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

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
public class ActivityCenterController extends BaseController {

    @Autowired
    private IActivityCenterService iActivityCenterService;

    /**
     * 获取活动列表
     */
    @GetMapping("/getList")
    public ResultInfo getActivityList(HttpServletRequest request) {
        return iActivityCenterService.getList(buildPage());
    }
    /**
     * 添加活动
     */
    @PostMapping("/addActivity")
    public ResultInfo addActivity(HttpServletRequest request, ActivityCenterVO activityCenter) {
        return iActivityCenterService.addActivity(request,activityCenter);
    }
    /**
     * 删除活动
     */
    @PostMapping("/delete")
    public ResultInfo delete(@RequestBody List<Integer> ids) {
        return iActivityCenterService.delete(ids);
    }

    /***
     * 编辑活动
     */
    @PostMapping("/updateActivity")
    public ResultInfo updateActivity(HttpServletRequest request,ActivityCenterVO activityCenter) {
        return iActivityCenterService.updateActivity(request,activityCenter);
    }

    /**
     * 禁用/启用活动
     */
    @PostMapping("/fixActivity")
    public ResultInfo fixActivity(@RequestBody FixActivityVO fixActivityVO) {
        return iActivityCenterService.fixActivity(fixActivityVO);
    }
    /**
     * 获取活动未绑定优惠券
     */
    @GetMapping("/getUnbindCoupon")
    public ResultInfo getUnbindCoupon(@RequestParam Integer id) {
        return iActivityCenterService.getUnbindCoupon(id,buildPage());
    }
    /**
     * 获取活动绑定的优惠券
     */
    @GetMapping("/getBindCoupon")
    public ResultInfo getBindCoupon(@RequestParam Integer id) {
        return iActivityCenterService.getBindCoupon(id,buildPage());
    }
    /**
     * 绑定解绑优惠券和活动
     */
    @PostMapping("/fixBindCoupon")
    public ResultInfo fixBindCoupon(@RequestBody FixBindCoupon fixBindCoupon) {
        return iActivityCenterService.fixBindCoupon(fixBindCoupon);
    }
}

