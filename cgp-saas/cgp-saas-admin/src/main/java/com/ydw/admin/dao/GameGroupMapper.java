package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.GameGroup;
import com.ydw.admin.model.vo.*;
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
public interface GameGroupMapper extends BaseMapper<GameGroup> {

//    List<GameGroupAppVO> getAppIdsByGameGroupId(@Param("id") Integer gameGroupId);

    Page<GameGroupAppVO> getAppsByGameGroupId(@Param("id") Integer gameGroupId, Page b);

    List<GameGroup> getByGameGroupId(@Param("id") Integer i);

    List<AppVO> getGameList();

    List<GameGroup> getAppIdsByGameGroupId(@Param("id") Integer gameGroupId);

    List<AppTagVO> getNewGameList();

    List<AppsConnectionsVO> getAppsConnectionsStatisticsTopTen(@Param("startTime") String startTime, @Param("endTime") String endTime);


    List<GameListsVO> getGameGroupListsExceptDefault();
}
