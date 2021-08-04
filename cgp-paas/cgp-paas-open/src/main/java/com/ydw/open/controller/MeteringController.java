package com.ydw.open.controller;


import com.ydw.open.service.ITbDeviceUsedService;
import com.ydw.open.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/metering")
public class MeteringController extends BaseController {

    @Autowired
    private ITbDeviceUsedService iTbDeviceUsedService;


    @RequestMapping(value = "/getUsedList", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getUsedList(HttpServletRequest request,
                                  @RequestParam(required = false) String deviceName,
                                  @RequestParam(required = false) String appName,
                                  @RequestParam(required = false) String enterpriseName,
                                  @RequestParam(required = false) String customId,
                                  @RequestParam(required = false) String fromIp,
                                  @RequestParam(required = false) String startTime,
                                  @RequestParam(required = false) String endTime,
                                  @RequestParam(required = false) Integer type,
                                  @RequestParam(required = false) Integer pageNum,
                                  @RequestParam(required = false) Integer pageSize) {
        return iTbDeviceUsedService.getUsedList(request, deviceName,appName,enterpriseName,customId,fromIp,startTime,endTime,type,buildPage());

    }

    /**
     *历史应用连接时长统计
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
     *历史应用连接时长统计TopTen
     * @param request
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping(value = "/getAppsConnectTimeStatisticsTopTen")
    @ResponseBody
    public ResultInfo getAppsConnectTimeStatisticsTopTen(HttpServletRequest request,
                                                   @RequestParam(required = false) String startTime,
                                                   @RequestParam(required = false) String endTime

                                                  ) {

        return iTbDeviceUsedService.getAppsConnectTimeStatisticsTopTen(request,startTime,endTime);

    }


    /**
     * 历史应用连接数统计
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

        return iTbDeviceUsedService.getAppsConnectionsStatisticsTopTen(request,startTime,endTime);

    }



    /**
     * 实时  应用连接数统计
     * @param request
     * @param
     * @param
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
     * 实时  应用连接数统计TOP10
     * @param request
     * @param
     * @param
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
         *应用连接数统计 折线图
         */
    @PostMapping(value = "/getAppsConnectionCount")
    @ResponseBody
    public ResultInfo getAppsConnectionCount(HttpServletRequest request,
                                             @RequestParam(required = false) String startTime,
                                             @RequestParam(required = false) String endTime,
                                             @RequestBody(required = false)List<String>ids) {


        return iTbDeviceUsedService.getAppsConnectionCount(request,startTime,endTime,ids);

    }
}
