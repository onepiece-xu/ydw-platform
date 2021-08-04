package com.ydw.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.TbClusters;

import com.ydw.user.model.vo.ClusterStatusVO;
import com.ydw.user.model.vo.CreateClusterVO;
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
public interface TbClustersMapper extends BaseMapper<TbClusters> {

    Page<CreateClusterVO> getClusters(
                                 @Param("name") String name,
                                 @Param("type") Integer type,
                                 @Param("isLocal") Integer isLocal,
                                 @Param("description") String description,
                                 @Param("apiUrl") String apiUrl,
                                 @Param("accessIp") String accessIp,
                                 @Param("schStatus") Integer schStatus,
                                 @Param("search") String search,Page buildPage);

    List<TbClusters> getAllClusters();

    ClusterStatusVO getClusterStatus(String id);
}
