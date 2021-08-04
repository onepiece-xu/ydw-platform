package com.ydw.open.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ydw.open.model.db.TbAppApprove;
import com.ydw.open.model.db.TbClusters;
import com.ydw.open.model.db.TbGameType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-05-19
 */
public interface TbAppApproveMapper extends BaseMapper<TbAppApprove> {

    Page<TbAppApprove> getList(@Param("identification") String identification, @Param("status") Integer  status , @Param("cooperationType")Integer cooperationType, @Param("search") String search,Page buildPage);

    TbAppApprove getAppApprove(String id);

    void updateSelective(TbAppApprove appApprove);


    List<TbGameType> getGameType();

    List<TbClusters> getClusterInfo();
}
