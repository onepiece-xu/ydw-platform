package com.ydw.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.user.model.db.TbDeviceGroupRelated;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-06-01
 */
public interface TbDeviceGroupRelatedMapper extends BaseMapper<TbDeviceGroupRelated> {

    TbDeviceGroupRelated getByGroupIdDeviceId(@Param("groupId") Integer groupId,@Param("deviceId")String deviceId);
}
