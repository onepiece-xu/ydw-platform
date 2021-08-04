package com.ydw.user.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.TbDeviceUsed;
import com.ydw.user.model.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author heao
 * @since 2020-05-06
 */
public interface TbDeviceUsedMapper extends BaseMapper<TbDeviceUsed> {

    List<ClusterConnectionsVO> getClusterConnectionsStatistics(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<ClusterConnectionsVO> getClusterConnectTimeStatistics(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Page<AppsConnectionsVO> getAppsConnectTimeStatistics(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("clusterId") String clusterId,Page buildPage);

    Page<AppsConnectionsVO> getAppsConnectionsStatistics(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("clusterId") String clusterId,Page buildPage);

    List<AppsConnectionsVO> getAppsConnectionsRealTime(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("clusterId") String clusterId,Page buildPage);


    DeviceUsedVO getUsedById(@Param("id") String id);

    List<ClusterConnectionsVO> getClusterConnectionsRealTime();

    List<AppsConnectionsVO> getAppsConnectionsStatisticsTopTen(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<AppsConnectionsVO> getAppsConnectTimeStatisticsTopTen(@Param("startTime") String startTime, @Param("endTime") String endTime);
    
    Page<DeviceUsedVO> getUsedList(@Param("deviceName")String deviceName, @Param("appName")String appName, @Param("enterpriseName")String enterpriseName, @Param("customId")String customId, @Param("fromIp")String fromIp
            , @Param("beginTime")String beginTime,@Param("endTime") String endTime,@Param("type")Integer type
            ,Page buildPage);

    List<DeviceUsageTrendVO> getDayDeviceUsage(@Param("dayTime")String day);

    Page<OnlineUserVO> getOnlineUsers(@Param("search")String search, Page buildPage);

    String getTotal();

    List<RecordListVO> getRecordPlayList(@Param("id")String id);

    ConnectSummaryVO getUsedSummary(@Param("deviceName")String deviceName,
                                    @Param("appName")String appName,
                                    @Param("enterpriseName")String enterpriseName,
                                    @Param("customId")String customId,
                                    @Param("fromIp")String fromIp,
                                    @Param("beginTime")String beginTime,
                                    @Param("endTime")String endTime,
                                    @Param("type")Integer type);

}
