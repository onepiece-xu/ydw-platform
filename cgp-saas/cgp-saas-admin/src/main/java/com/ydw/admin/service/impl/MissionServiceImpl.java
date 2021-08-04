package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.dao.MissionMapper;
import com.ydw.admin.dao.UserMissionMapper;
import com.ydw.admin.model.db.Mission;

import com.ydw.admin.model.db.UserMission;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IMissionService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-06-01
 */
@Service
public class MissionServiceImpl extends ServiceImpl<MissionMapper, Mission> implements IMissionService {
    @Autowired
    private MissionMapper missionMapper;
    @Autowired
    private UserMissionMapper userMissionMapper;

    @Override
    public ResultInfo clearMission() {
        //查询已完成的状态记录
        List<UserMission> endMission = missionMapper.getEndMission();
        for(UserMission um:endMission){
            //重置为未完成
            um.setStatus(0);
            userMissionMapper.updateById(um);
        }
        return ResultInfo.success();
    }
}
