package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.admin.model.db.Clusters;
import com.ydw.admin.model.vo.ClusterVO;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
public interface ClustersMapper extends BaseMapper<Clusters> {

    public List<ClusterVO> getUseableRegions();
}
