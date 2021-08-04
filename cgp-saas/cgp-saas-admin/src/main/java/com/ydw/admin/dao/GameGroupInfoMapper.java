package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.GameGroup;
import com.ydw.admin.model.db.GameGroupInfo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-07-16
 */
public interface GameGroupInfoMapper extends BaseMapper<GameGroupInfo> {

    Page<GameGroup> getGameGroupList(Page b);
}
