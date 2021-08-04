package com.ydw.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.dao.ActivityCouponMapper;
import com.ydw.platform.model.db.ActivityCenter;
import com.ydw.platform.dao.ActivityCenterMapper;
import com.ydw.platform.model.db.ActivityCoupon;
import com.ydw.platform.model.db.CouponCard;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IActivityCenterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-05-27
 */
@Service
public class ActivityCenterServiceImpl extends ServiceImpl<ActivityCenterMapper, ActivityCenter> implements IActivityCenterService {
    @Autowired
    private ActivityCenterMapper activityCenterMapper;
    @Autowired
    private ActivityCouponMapper activityCouponMapper;
    @Override
    public ResultInfo getActivityList(Page buildPage) {

        QueryWrapper<ActivityCenter> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        wrapper.eq("type",1);
        Page page = activityCenterMapper.selectPage(buildPage, wrapper);

        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo getActivityCoupon(Integer id) {
//        QueryWrapper<ActivityCoupon> wrapper = new QueryWrapper<>();
//        List<ActivityCoupon> activityCoupons = activityCouponMapper.selectList(wrapper);
        List<CouponCard> couponByActivityId = activityCouponMapper.getCouponByActivityId(id);
        return ResultInfo.success(couponByActivityId);
    }
}
