package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.admin.model.db.Mission;
import com.ydw.admin.model.db.UserMission;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2021-06-01
 */
public interface MissionMapper extends BaseMapper<Mission> {


    List<UserMission> getEndMission();
}
