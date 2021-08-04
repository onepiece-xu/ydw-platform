package com.ydw.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.user.model.db.TbGameGroup;

import com.ydw.user.model.vo.AppTagVO;
import com.ydw.user.model.vo.AppsConnectionsVO;
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

    List<AppsConnectionsVO> getAppsConnectTimeStatisticsTopTen(@Param("startTime") String startTime,@Param("endTime")  String endTime);

    List<AppsConnectionsVO> getAppsConnectionsStatisticsTopTen(@Param("startTime") String startTime,@Param("endTime")  String endTime);

    List<AppTagVO> getNewGameList();
}
