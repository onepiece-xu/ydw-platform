package com.ydw.resource.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.resource.model.db.DeviceApps;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-08-03
 */
public interface DeviceAppsMapper extends BaseMapper<DeviceApps> {

    List<String> getDeviceUninstallApps(@Param(value = "deviceId") String deviceId);
}
