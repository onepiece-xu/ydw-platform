package com.ydw.resource.dao;

import com.ydw.resource.model.db.Clusters;
import com.ydw.resource.model.vo.ClusterVO;
import com.ydw.resource.model.vo.WebrtcConfig;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-07-30
 */
public interface ClustersMapper extends BaseMapper<Clusters> {

	List<ClusterVO> getClustersByApp(@Param("identification") String identification, @Param("appId") String appId);

	List<ClusterVO> getClusters(@Param("identification") String identification);

	WebrtcConfig getClusterWebrtcConfig(@Param("clusterId") String clusterId);

	List<Clusters> getClustersByAppId(String appId);
}
