package com.ydw.resource.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.resource.model.db.Devices;
import com.ydw.resource.model.vo.DeviceInfo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-07-30
 */
public interface DevicesMapper extends BaseMapper<Devices> {

    DeviceInfo getDeviceInfo(@Param("devid") String devid);
    
    List<DeviceInfo> getDeviceInfos(List<String> devid);

}
