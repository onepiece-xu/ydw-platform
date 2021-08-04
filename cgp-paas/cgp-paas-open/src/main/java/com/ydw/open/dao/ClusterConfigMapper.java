package com.ydw.open.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.open.model.db.ClusterConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-08-07
 */
public interface ClusterConfigMapper extends BaseMapper<ClusterConfig> {

    List<ClusterConfig> getByConfigId(@Param("id") Integer i);

//    ClusterConfig getByClusterAndConfig(@Param("clusterId")String clusterId, @Param("configId")Integer configId);

    ClusterConfig getByClusterId(String clusterId);
}
