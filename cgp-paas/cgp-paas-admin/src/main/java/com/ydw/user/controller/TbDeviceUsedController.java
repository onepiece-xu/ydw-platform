package com.ydw.user.controller;


import com.ydw.user.model.db.TbDeviceUsed;
import com.ydw.user.service.ITbDeviceUsedService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-05-06
 */
@Controller
@RequestMapping("/v1/deviceUsed")
public class TbDeviceUsedController  extends BaseController{

    @Autowired
    private ITbDeviceUsedService iTbDeviceUsedService;

    @RequestMapping(value = "/createUsed", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo createUsed(HttpServletRequest request, @RequestBody TbDeviceUsed used) {
        return iTbDeviceUsedService.createUsed(request, used);
    }

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
    @RequestMapping(value = "/getUsedListById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getUsedList(HttpServletRequest request,@PathVariable String id) {
        return iTbDeviceUsedService.getUsedListById(request, id);
    }

    /**
     * 获取最近玩过的历史游戏记录
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/getRecordPlayList", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getRecordPlayList(HttpServletRequest request,@RequestParam String id) {
        return iTbDeviceUsedService.getRecordPlayList(request, id);
    }

    @RequestMapping(value = "/getUsedSummary", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getUsedSummary(@RequestParam(required = false) String deviceName,
                                  @RequestParam(required = false) String appName,
                                  @RequestParam(required = false) String enterpriseName,
                                  @RequestParam(required = false) String customId,
                                  @RequestParam(required = false) String fromIp,
                                  @RequestParam(required = false) String startTime,
                                  @RequestParam(required = false) String endTime,
                                  @RequestParam(required = false) Integer type) {
        return iTbDeviceUsedService.getUsedSummary(deviceName,appName,enterpriseName,customId,fromIp,startTime,endTime,type);

    }

}

