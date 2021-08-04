package com.ydw.user.controller;


import com.ydw.user.model.vo.AppQueueVO;
import com.ydw.user.service.ITbDeviceUsedService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/v1/monitor")
public class MonitorController  extends BaseController{

    @Autowired
    private ITbDeviceUsedService iTbDeviceUsedService;

    /**
     * 历史集群连接数统计
     *
     * @param request
     * @param startTime
     * @param endTime
     * @return
     */

    @GetMapping(value = "/getClusterConnectionsStatistics")


    @ResponseBody
    public ResultInfo getClusterConnectionsStatistics(HttpServletRequest request,
                                                      @RequestParam(required = false) String startTime,
                                                      @RequestParam(required = false) String endTime) {

        return iTbDeviceUsedService.getClusterConnectionsStatistics(request, startTime, endTime);

    }

    /**
     * 历史节点连接时长统计
     *
     * @param request
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping(value = "/getClusterConnectTimeStatistics")
    @ResponseBody
    public ResultInfo getClusterConnectTimeStatistics(HttpServletRequest request,
                                                      @RequestParam(required = false) String startTime,
                                                      @RequestParam(required = false) String endTime) {

        return iTbDeviceUsedService.getClusterConnectTimeStatistics(request, startTime, endTime);

    }

    /**
     * 历史应用连接时长统计
     *
     * @param request
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping(value = "/getAppsConnectTimeStatistics")
    @ResponseBody
    public ResultInfo getAppsConnectTimeStatistics(HttpServletRequest request,
                                                   @RequestParam(required = false) String startTime,
                                                   @RequestParam(required = false) String endTime,
                                                   @RequestParam(required = false) String clusterId,
                                                   @RequestParam(required = false) Integer pageNum,
                                                   @RequestParam(required = false) Integer pageSize) {

        return iTbDeviceUsedService.getAppsConnectTimeStatistics(request, startTime, endTime, clusterId,buildPage());

    }

    /**
     * 历史应用连接时长统计
     *
     * @param request
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping(value = "/getAppsConnectTimeStatisticsTopTen")
    @ResponseBody
    public ResultInfo getAppsConnectTimeStatisticsTopTen(HttpServletRequest request,
                                                   @RequestParam(required = false) String startTime,
                                                   @RequestParam(required = false) String endTime) {

        return iTbDeviceUsedService.getAppsConnectTimeStatisticsTopTen(request, startTime, endTime);

    }

    /**
     * 历史应用连接数统计
     *
     * @param request
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping(value = "/getAppsConnectionsStatistics")
    @ResponseBody
    public ResultInfo getAppsConnectionsStatistics(HttpServletRequest request,
                                                   @RequestParam(required = false) String startTime,
                                                   @RequestParam(required = false) String endTime,
                                                   @RequestParam(required = false) String clusterId,
                                                   @RequestParam(required = false) Integer pageNum,
                                                   @RequestParam(required = false) Integer pageSize) {

        return iTbDeviceUsedService.getAppsConnectionsStatistics(request, startTime, endTime, clusterId,buildPage());

    }


    /**
     * 历史应用连接数统计top10
     *
     * @param request
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping(value = "/getAppsConnectionsStatisticsTopTen")
    @ResponseBody
    public ResultInfo getAppsConnectionsStatisticsTopTen(HttpServletRequest request,
                                                   @RequestParam(required = false) String startTime,
                                                   @RequestParam(required = false) String endTime) {

        return iTbDeviceUsedService.getAppsConnectionsStatisticsTopTen(request, startTime, endTime);

    }



    /**
     * 实时  应用连接数统计
     *
     * @param request
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping(value = "/getAppsConnectionsRealTime")
    @ResponseBody
    public ResultInfo getAppsConnectionsRealTime(HttpServletRequest request,
                                                 @RequestParam(required = false) String startTime,
                                                 @RequestParam(required = false) String endTime,
                                                 @RequestParam(required = false) String clusterId,
                                                 @RequestParam(required = false) Integer pageNum,
                                                 @RequestParam(required = false) Integer pageSize) {

        return iTbDeviceUsedService.getAppsConnectionsRealTime(request, startTime, endTime, clusterId,buildPage());

    }


    /**
     * 实时  应用连接数统计
     *
     * @param request
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping(value = "/getAppsConnectionsRealTimeTopTen")
    @ResponseBody
    public ResultInfo getAppsConnectionsRealTimeTopTen(HttpServletRequest request,
                                                 @RequestParam(required = false) String startTime,
                                                 @RequestParam(required = false) String endTime) {

        return iTbDeviceUsedService.getAppsConnectionsRealTimeTopTen(request, startTime, endTime);

    }



    /**
     * 设备状态统计
     *
     * @param request
     * @param
     * @return
     */
    @GetMapping(value = "/getDeviceStatusCount")
    @ResponseBody
    public ResultInfo getDeviceStatusCount(HttpServletRequest request, @RequestParam(required = false) String clusterId) {

        return iTbDeviceUsedService.getDeviceStatusCount(request, clusterId);

    }


    /**
     * 设备利用率（实时）
     *
     * @param request
     * @param
     * @return
     */
    @GetMapping(value = "/getDeviceUsageRate")
    @ResponseBody
    public ResultInfo getDeviceUsageRate(HttpServletRequest request, @RequestParam(required = false) String clusterId,
                                         @RequestParam(required = false) Integer pageNum,
                                         @RequestParam(required = false) Integer pageSize) {

        return iTbDeviceUsedService.getDeviceUsageRate(request, clusterId,buildPage());

    }

    /**
     * 历史设备利用率统计
     *
     * @param request
     * @param
     * @return
     */
    @GetMapping(value = "/getDeviceUsageHistory")
    @ResponseBody
    public ResultInfo getDeviceUsageHistory(HttpServletRequest request, @RequestParam(required = false) String startTime,
                                            @RequestParam(required = false) String endTime,
                                            @RequestParam(required = false) String clusterId,
                                            @RequestParam(required = false) Integer pageNum,
                                            @RequestParam(required = false) Integer pageSize) {

        return iTbDeviceUsedService.getDeviceUsageHistory(request, startTime, endTime, clusterId,buildPage());

    }

    /**
     * 实时连接数统计
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/getClusterConnectionsRealTime")
    @ResponseBody
    public ResultInfo getClusterConnectionsRealTime(HttpServletRequest request) {

        return iTbDeviceUsedService.getClusterConnectionsRealTime(request);

    }

    /**
     * 总利用率
     */
    @GetMapping(value = "/getTotalUsage")
    @ResponseBody
    public ResultInfo getTotalUsage(HttpServletRequest request, @RequestParam(required = false) String startTime,
                                    @RequestParam(required = false) String endTime) {

        return iTbDeviceUsedService.getTotalUsage(request, startTime, endTime);

    }

    /**
     * 应用安装统计
     */
    @GetMapping(value = "/getAppInstallCount")
    @ResponseBody
    public ResultInfo getAppInstallCount(HttpServletRequest request, @RequestParam(required = false) Integer pageNum,
                                         @RequestParam(required = false) Integer pageSize) {

        return iTbDeviceUsedService.getAppInstallCount(request ,buildPage());

    }

    /**
     * 获取各集群设备状态统计
     *
     */
    @GetMapping(value = "/getClusterDevicesStatus")
    @ResponseBody
    public ResultInfo getClusterDevicesStatus(HttpServletRequest request) {

        return iTbDeviceUsedService.getClusterDevicesStatus(request);

    }

    /**
     * 获取利用率趋势图
     * @param request
     * @return
     */
    @GetMapping(value = "/getUsageTrend")
    @ResponseBody
    public ResultInfo getUsageTrend(HttpServletRequest request,@RequestParam(required = false) String startTime,
                                    @RequestParam(required = false) String endTime) {

        return iTbDeviceUsedService.getUsageTrend(request,startTime,endTime);

    }

    /**
     * 应用排队情况统计
     */
    @GetMapping(value = "/getAppQueueNum")
    @ResponseBody
    public ResultInfo getAppQueueNum(HttpServletRequest request,@RequestParam(required = false)Integer pageNum,  @RequestParam(required = false)Integer pageSize,
                                     @RequestParam(required = false)String search) {

        return iTbDeviceUsedService.getAppQueueNum(request,buildPage(),search);

    }


    /**
     * 清理游戏对应区服中的排队僵尸
     */
    @PostMapping(value = "/clearQueueCorpse")
    @ResponseBody
    public ResultInfo clearQueueCorpse(HttpServletRequest request,@RequestBody List<AppQueueVO> vos) {

        return iTbDeviceUsedService.clearQueueCorpse(request ,vos);

    }

    /**
     * 在线用户相关信息
     */
    @GetMapping(value = "/getOnlineUsers")
    @ResponseBody
    public ResultInfo getOnlineUsers(HttpServletRequest request,@RequestParam(required = false)Integer pageNum,  @RequestParam(required = false)Integer pageSize,
                                     @RequestParam(required = false)String search) {

        return iTbDeviceUsedService.getOnlineUsers(request,buildPage(),search);

    }

}
