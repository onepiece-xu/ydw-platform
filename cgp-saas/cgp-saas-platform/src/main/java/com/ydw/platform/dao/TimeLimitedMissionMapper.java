package com.ydw.platform.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.TimeLimitedMission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.platform.model.vo.TimeLimitedMissionVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2021-07-06
 */
public interface TimeLimitedMissionMapper extends BaseMapper<TimeLimitedMission> {

    Page<TimeLimitedMissionVO> getMissionList(String userId, Page buildPage);
}
