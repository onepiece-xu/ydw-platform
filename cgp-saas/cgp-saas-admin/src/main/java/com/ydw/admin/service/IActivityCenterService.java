package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.vo.ActivityCenterVO;
import com.ydw.admin.model.vo.FixActivityVO;
import com.ydw.admin.model.vo.FixBindCoupon;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.model.db.ActivityCenter;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-05-27
 */
public interface IActivityCenterService extends IService<ActivityCenter> {

    ResultInfo getList(Page buildPage);

    ResultInfo addActivity(HttpServletRequest request, ActivityCenterVO activityCenter);

    ResultInfo delete(List<Integer> ids);

    ResultInfo updateActivity(HttpServletRequest request,ActivityCenterVO activityCenter);

    ResultInfo fixActivity(FixActivityVO fixActivityVO);

    ResultInfo getBindCoupon(Integer id, Page buildPage);

    ResultInfo getUnbindCoupon(Integer id, Page buildPage);


    ResultInfo fixBindCoupon(FixBindCoupon fixBindCoupon);
}
