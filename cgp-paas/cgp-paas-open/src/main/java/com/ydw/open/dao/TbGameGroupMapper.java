package com.ydw.open.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ydw.open.model.db.TbGameGroup;
import com.ydw.open.model.vo.AppTagVO;
import com.ydw.open.model.vo.AppVO;
import com.ydw.open.model.vo.AppsConnectionsVO;
import com.ydw.open.model.vo.GameGroupAppVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-07-14
 */
public interface TbGameGroupMapper extends BaseMapper<TbGameGroup> {

    List<TbGameGroup> getAppIdsByGameGroupId(@Param("id") Integer gameGroupId);

    List<AppsConnectionsVO> getAppsConnectTimeStatisticsTopTen(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<AppsConnectionsVO> getAppsConnectionsStatisticsTopTen(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<AppTagVO> getNewGameList();


    Page<GameGroupAppVO> getAppsByGameGroupId(@Param("id") Integer gameGroupId, Page buildPage);

    List<TbGameGroup> getByGameGroupId(@Param("id")Integer i);

    List<AppVO> getGameList();
}
