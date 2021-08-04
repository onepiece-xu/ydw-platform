package com.ydw.platform.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.Mission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.platform.model.vo.MissionVO;

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

    Page<MissionVO> getMissionList(String userId, Page buildPage);

    Double getUserRechargeAmount(String userId);
}
