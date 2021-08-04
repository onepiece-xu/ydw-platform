package com.ydw.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ydw.user.model.db.TbDeviceApps;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-04-14
 */
public interface TbDeviceAppsMapper extends BaseMapper<TbDeviceApps> {
    int insertSelective(TbDeviceApps app);

    int updateSelective(TbDeviceApps app);

    Page<TbDeviceApps> getAppList(@Param("deviceId")String deviceId,
                                  @Param("appId")Integer appId,
                                  @Param("status") Integer status,Page buildPage);

    TbDeviceApps getDeviceApp(@Param("deviceId")String deviceId,  @Param("appId")String id);

    List<TbDeviceApps> getDevicesByAppId(String id);
}
