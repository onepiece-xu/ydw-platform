package com.ydw.open.dao;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.open.model.db.TbUserAppsRelated;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.open.model.vo.App;
import com.ydw.open.model.vo.AppUseApproveVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-08-06
 */
public interface TbUserAppsRelatedMapper extends BaseMapper<TbUserAppsRelated> {

    Page<AppUseApproveVO> getAppApproveList(String body, @Param("search")String search , Page buildPage);

    Integer  getRelatedIdByAppIdAndEnterpriseId(@Param("appId") String toString, @Param("enterpriseId") String enterpriseId);

    Page<AppUseApproveVO> getAppApproves(String body, Page buildPage,@Param("id") String id);


    List<App> getOwnerAppList(@Param("id")String enterpriseId);

    Page<AppUseApproveVO> getGameListApproved(@Param("search")String body, Page buildPage,@Param("enterpriseId") String enterpriseId);

    Page<AppUseApproveVO> getAppApproved(@Param("id")String body, @Param("search")String search , Page buildPage);
}
