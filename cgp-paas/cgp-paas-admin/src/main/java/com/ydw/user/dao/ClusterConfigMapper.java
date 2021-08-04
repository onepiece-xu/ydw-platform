package com.ydw.user.dao;

import com.ydw.user.model.db.ClusterConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
