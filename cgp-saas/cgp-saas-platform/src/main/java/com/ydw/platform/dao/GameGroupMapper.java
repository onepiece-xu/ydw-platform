package com.ydw.platform.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.GameGroup;
import com.ydw.platform.model.vo.*;

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



    Page<GameGroupAppVO> getAppsByGameGroupId(@Param("id") Integer gameGroupId,Page b);

    List<GameGroup> getByGameGroupId(@Param("id") Integer i);

    List<AppVO> getGameList();

    List<GameGroup> getAppIdsByGameGroupId(@Param("id") Integer gameGroupId);

    List<NewGameVOAndorid> getNewGameList();

    List<NewGamesVO> newGameList();

    List<AppsConnectionsVO> getAppsConnectionsStatisticsTopTen(@Param("startTime") String startTime, @Param("endTime") String endTime);


    List<RecommendListVO> getRecommendList(@Param("id")Integer gamegroupId, Page page);

    Page<GameGroupListsVO> getGameGroupLists(@Param("id")Integer gamegroupId, Page page);

    List<HotGameTag> getHotGameList(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<RecommendAppVO> getRecommendApps(Integer gamegroupId);

    List<HotGameApps> getHotGameApps(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<NewAppsVO> getNewApps();

    List<TopAppVO> getTopApps(Integer gamegroupId);

    List<GameGroupAppsVO> getGameGroupApps(@Param("id") Integer gamegroupId);
}
