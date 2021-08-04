package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbDeviceUsed;

import com.ydw.user.model.vo.AppQueueVO;
import com.ydw.user.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-05-06
 */
public interface ITbDeviceUsedService extends IService<TbDeviceUsed> {

    ResultInfo createUsed(HttpServletRequest request, TbDeviceUsed used);

    ResultInfo getUsedList(HttpServletRequest request, String  deviceName, String appName, String enterpriseName, String customId,
                           String fromIp,String startTime, String endTime,Integer type, Page buildPage);

    ResultInfo getClusterConnectionsStatistics(HttpServletRequest request,String startTime,String endTime);

    ResultInfo getClusterConnectTimeStatistics(HttpServletRequest request, String startTime, String endTime);

    ResultInfo getAppsConnectTimeStatistics(HttpServletRequest request, String startTime, String endTime, String clusterId, Page buildPage);

    ResultInfo getAppsConnectionsStatistics(HttpServletRequest request, String startTime, String endTime, String clusterId,Page buildPage);

    ResultInfo getAppsConnectionsRealTime(HttpServletRequest request, String startTime, String endTime, String clusterId,Page buildPage);

    ResultInfo getDeviceStatusCount(HttpServletRequest request, String clusterId);

    ResultInfo getDeviceUsageRate(HttpServletRequest request,String clusterId,Page buildPag);

    ResultInfo getDeviceUsageHistory(HttpServletRequest request, String startTime, String endTime, String clusterId, Page buildPage);

    ResultInfo getUsedListById(HttpServletRequest request, String id);

    ResultInfo getClusterConnectionsRealTime(HttpServletRequest request);

    ResultInfo getTotalUsage(HttpServletRequest request, String startTime, String endTime);

    ResultInfo getAppInstallCount(HttpServletRequest request, Page buildPag);

    ResultInfo getClusterDevicesStatus(HttpServletRequest request);

    ResultInfo getAppsConnectionsRealTimeTopTen(HttpServletRequest request, String startTime, String endTime);

    ResultInfo getAppsConnectionsStatisticsTopTen(HttpServletRequest request, String startTime, String endTime);

    ResultInfo getAppsConnectTimeStatisticsTopTen(HttpServletRequest request, String startTime, String endTime);

    ResultInfo getUsageTrend(HttpServletRequest request, String startTime, String endTime);

    ResultInfo getAppQueueNum(HttpServletRequest request,Page buildPag,String search);

    ResultInfo clearQueueCorpse(HttpServletRequest request, List<AppQueueVO> vos);

    ResultInfo getOnlineUsers(HttpServletRequest request, Page buildPag, String search);

    ResultInfo getRecordPlayList(HttpServletRequest request, String id);

    ResultInfo getUsedSummary(String deviceName, String appName, String enterpriseName, String customId, String fromIp, String startTime, String endTime, Integer type);
}
