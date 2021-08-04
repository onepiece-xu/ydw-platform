package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.ActivityCenter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.vo.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-05-27
 */
public interface IActivityCenterService extends IService<ActivityCenter> {

    ResultInfo getActivityList(Page buildPage);

    ResultInfo getActivityCoupon(Integer id);
}
