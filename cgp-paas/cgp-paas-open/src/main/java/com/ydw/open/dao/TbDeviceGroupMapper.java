package com.ydw.open.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.open.model.db.TbDeviceGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-05-13
 */
public interface TbDeviceGroupMapper extends BaseMapper<TbDeviceGroup> {

    Page<TbDeviceGroup> getDeviceGroupList(@Param("name") String name, @Param("description") String description, @Param("schStatus") Integer schStatus, @Param("search") String search, Page buildPage);

    List<TbDeviceGroup> getGroupByClusterId(String id);
}
