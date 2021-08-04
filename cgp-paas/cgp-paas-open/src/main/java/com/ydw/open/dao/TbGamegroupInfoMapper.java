package com.ydw.open.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import com.ydw.open.model.db.TbGameGroup;
import com.ydw.open.model.db.TbGamegroupInfo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-07-16
 */
public interface TbGamegroupInfoMapper extends BaseMapper<TbGamegroupInfo> {

    Page<TbGameGroup> getGameGroupList(Page buildPage);
}
