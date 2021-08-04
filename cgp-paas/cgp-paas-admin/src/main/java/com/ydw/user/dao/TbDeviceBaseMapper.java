package com.ydw.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.TbDeviceBase;
import com.ydw.user.model.db.TbDevices;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-07-24
 */
public interface TbDeviceBaseMapper extends BaseMapper<TbDeviceBase> {

    Page<TbDeviceBase> getBaseList(@Param("search") String search, Page buildPage);

    TbDeviceBase getBaseById(@Param("id") Integer id);

    List<TbDevices> getDevicesByBaseId(@Param("id") Integer id);
}
