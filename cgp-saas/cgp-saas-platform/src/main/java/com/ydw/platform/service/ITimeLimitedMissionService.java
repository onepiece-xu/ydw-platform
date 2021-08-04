package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.TimeLimitedMission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.vo.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-07-06
 */
public interface ITimeLimitedMissionService extends IService<TimeLimitedMission> {

    ResultInfo getList(String userId, Page buildPage);


    ResultInfo changeStatus(String userId, TimeLimitedMission timeLimitedMission);
}
