package com.ydw.schedule.dao;

import com.ydw.schedule.model.db.TbDeviceApps;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-05-13
 */
public interface TbDeviceAppsMapper extends BaseMapper<TbDeviceApps> {

	List<String> getTbDeviceAppsByApp(@Param("clusterId")String clusterId, @Param("appId")String appId);

}
