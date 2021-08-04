package com.ydw.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.dao.LimitedMissionCouponMapper;
import com.ydw.platform.dao.UserLimitedMissionMapper;
import com.ydw.platform.model.db.*;
import com.ydw.platform.dao.TimeLimitedMissionMapper;
import com.ydw.platform.model.vo.MissionVO;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.model.vo.TimeLimitedMissionVO;
import com.ydw.platform.service.IPostMessageService;
import com.ydw.platform.service.ITemplateTypeService;
import com.ydw.platform.service.ITimeLimitedMissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.service.IYdwRechargeService;
import com.ydw.platform.util.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-07-06
 */
@Service
public class TimeLimitedMissionServiceImpl extends ServiceImpl<TimeLimitedMissionMapper, TimeLimitedMission> implements ITimeLimitedMissionService {
    @Autowired
    private TimeLimitedMissionMapper timeLimitedMissionMapper;
    @Autowired
    private UserLimitedMissionMapper userLimitedMissionMapper;
    @Autowired
    private LimitedMissionCouponMapper limitedMissionCouponMapper;
    @Autowired
    private ITemplateTypeService templateTypeService;

    @Autowired
    private IPostMessageService iPostMessageService;

    @Autowired
    private IYdwRechargeService iYdwRechargeService;

    @Override
    public ResultInfo getList(String userId, Page buildPage) {
//        userId="6798441011046746731";
        QueryWrapper<UserLimitedMission> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);

        Page page = userLimitedMissionMapper.selectPage(buildPage, wrapper);
        List records = page.getRecords();
        int size = records.size();
        if(size==0){
            QueryWrapper<TimeLimitedMission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("valid",1);
            Date date = new Date();
            queryWrapper.lt("begin_time",date);
            queryWrapper.gt("end_time",date);


            List<TimeLimitedMission> missions = timeLimitedMissionMapper.selectList(queryWrapper);
            //给当前用户添加任务
            for(TimeLimitedMission m:missions){
                UserLimitedMission userMission = new UserLimitedMission();
                userMission.setId(SequenceGenerator.sequence());
                userMission.setUserId(userId);
                userMission.setStatus(0);
                userMission.setMissionId(m.getId());
                userLimitedMissionMapper.insert(userMission);
            }
        }
        //查询出用户列表
        Page<TimeLimitedMissionVO> missionList = timeLimitedMissionMapper.getMissionList(userId, buildPage);
        List<TimeLimitedMissionVO> list = missionList.getRecords();
        for(TimeLimitedMissionVO vo:list){
            String voId = vo.getId();

            List<CouponCard> couponList = limitedMissionCouponMapper.getCouponList(voId);
            vo.setCouponCardList(couponList);
            QueryWrapper<UserLimitedMission> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id",userId);
            queryWrapper.eq("mission_id",voId);
            UserLimitedMission userLimitedMission = userLimitedMissionMapper.selectOne(queryWrapper);
            userLimitedMission.getStatus();
            vo.setStatus(userLimitedMission.getStatus());
        }
        return ResultInfo.success(missionList);

    }

    @Override
    public ResultInfo changeStatus(String userId, TimeLimitedMission timeLimitedMission) {
//        userId="777";
        Map<String,Object> message = new HashMap<>();
        message.put("userId",userId);
        String id = timeLimitedMission.getId();
        Integer status = timeLimitedMission.getStatus();

        QueryWrapper<UserLimitedMission> wrapper = new QueryWrapper<>();
        wrapper.eq("mission_id",id);
        wrapper.eq("user_id",userId);
        UserLimitedMission userLimitedMission = userLimitedMissionMapper.selectOne(wrapper);
        userLimitedMission.setStatus(status);
        //1 已完成待领取 2 已领取
        if(status==2){
            //发券
            List<CouponCard> couponList = limitedMissionCouponMapper.getCouponList(id);
            for(CouponCard cc: couponList){
                String  couponId= cc.getId();
                HashMap<String,String> params = new HashMap<>();
            params.put("id", couponId);
            params.put("userId",userId);

                    iYdwRechargeService.drawCoupon(params);

            }

            try {
                //添加站内信
                ResultInfo signin = templateTypeService.getTemplateId("time_limit_share");
                message.put("templateId", signin.getData());
                //发送类型为系统通知2
                message.put("type", 2);
                iPostMessageService.add(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //更新
        userLimitedMissionMapper.updateById(userLimitedMission);
        return ResultInfo.success();
    }
}
