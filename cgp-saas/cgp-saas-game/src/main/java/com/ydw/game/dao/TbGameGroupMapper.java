package com.ydw.game.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ydw.game.model.db.TbGameGroup;
import com.ydw.game.model.vo.AppTagVO;
import com.ydw.game.model.vo.AppVO;
import com.ydw.game.model.vo.AppsConnectionsVO;
import com.ydw.game.model.vo.GameGroupAppVO;
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

//    List<GameGroupAppVO> getAppIdsByGameGroupId(@Param("id") Integer gameGroupId);

    Page<GameGroupAppVO> getAppsByGameGroupId(@Param("id") Integer gameGroupId,Page b);

    List<TbGameGroup> getByGameGroupId(@Param("id") Integer i);

    List<AppVO> getGameList();

    List<TbGameGroup> getAppIdsByGameGroupId(@Param("id") Integer gameGroupId);

    List<AppTagVO> getNewGameList();

    List<AppsConnectionsVO> getAppsConnectionsStatisticsTopTen(@Param("startTime") String startTime, @Param("endTime") String endTime);


}
