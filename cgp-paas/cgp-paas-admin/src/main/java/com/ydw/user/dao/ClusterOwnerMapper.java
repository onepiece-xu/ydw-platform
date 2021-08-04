package com.ydw.user.dao;

import com.ydw.user.model.db.ClusterOwner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-11-16
 */
public interface ClusterOwnerMapper extends BaseMapper<ClusterOwner> {

    List<String> getOwnerByCluster(String id);


    List<String> getClusterByUser(String id);
}
