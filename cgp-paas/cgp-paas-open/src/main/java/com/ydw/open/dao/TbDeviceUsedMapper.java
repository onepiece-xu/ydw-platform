package com.ydw.open.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.open.model.db.TbDeviceUsed;
import com.ydw.open.model.vo.*;
//import com.ydw.platform.model.vo.AppsConnectionsVO;
//import com.ydw.platform.model.vo.ClusterConnectionsVO;
import com.ydw.open.model.vo.DeviceUsedVO;
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



    Page<MeteringVO> getUsedListByIdentification(@Param("appName")String appName, @Param("startTime")String startTime
            , @Param("endTime")String endTime, @Param("type")Integer type, @Param("identification")String identification, Page buildPage);

    List<AppCountVO> getApps(String identification );

    List<TrendVO> getAppsConnectionCount(@Param("identification")String identification, @Param("appIds")List<String> appIds, @Param("days")List<String> days);

    List<ClusterConnectionsVO> getClusterConnectionsStatistics(@Param("identification") String identification,@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<ClusterConnectionsVO> getClusterConnectTimeStatistics(@Param("startTime") String startTime, @Param("endTime") String endTime,@Param("identification") String identification);

    Page<AppsConnectionsVO> getAppsConnectTimeStatistics(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("clusterId") String clusterId,Page buildPage);

    Page<AppsConnectionsVO> getAppsConnectionsStatistics(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("clusterId") String clusterId,Page buildPage);

    List<AppsConnectionsVO> getAppsConnectionsRealTime(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("clusterId") String clusterId,Page buildPage);


    DeviceUsedVO getUsedById(@Param("id") String id);

    List<ClusterConnectionsVO> getClusterConnectionsRealTime(String identification);

    List<AppsConnectionsVO> getAppsConnectionsStatisticsTopTen(@Param("startTime") String startTime, @Param("endTime") String endTime);

    List<AppsConnectionsVO> getAppsConnectTimeStatisticsTopTen(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Page<DeviceUsedVO> getUsedList(@Param("deviceName")String deviceName, @Param("appName")String appName, @Param("enterpriseName")String enterpriseName, @Param("customId")String customId, @Param("fromIp")String fromIp
            , @Param("beginTime")String beginTime,@Param("endTime") String endTime,@Param("type")Integer type,@Param("identification") String identification,Page  buildPage);

    List<DeviceUsageTrendVO> getDayDeviceUsage(@Param("identification")String identification,@Param("dayTime")String day);

    Page<OnlineUserVO> getOnlineUsers(@Param("search")String search, Page buildPage);



    List<RecordListVO> getRecordPlayList(@Param("id")String id);


    Page<NetbarUsedListVO> getUsedListNetbar(@Param("identification")String identification, @Param("netbarName")String netbarName,@Param("beginTime") String startTime
            , @Param("endTime")String endTime, @Param("type")Integer type,Page buildPage);

    String getTotalTime(@Param("identification")String identification,  @Param("netbarName")String netbarName, @Param("beginTime")String startTime, @Param("endTime")String endTime, @Param("type")Integer type);

//    String  getTotalTime(String identification);
}
