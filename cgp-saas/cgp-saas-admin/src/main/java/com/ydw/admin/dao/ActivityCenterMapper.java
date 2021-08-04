package com.ydw.admin.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.ActivityCenter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.admin.model.vo.ActivityCenterVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2021-05-27
 */
public interface ActivityCenterMapper extends BaseMapper<ActivityCenter> {

    Page<ActivityCenterVO> getList(Page buildPage);

}
