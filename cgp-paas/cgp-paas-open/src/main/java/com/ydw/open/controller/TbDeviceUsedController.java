package com.ydw.open.controller;


import com.ydw.open.model.db.TbDeviceUsed;
import com.ydw.open.service.ITbDeviceUsedService;
import com.ydw.open.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/deviceUsed")
public class TbDeviceUsedController extends BaseController {

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
     * open平台 云网吧服务 消费统计
     * @param request
     * @param deviceName
     * @param appName
     * @param enterpriseName
     * @param customId
     * @param fromIp
     * @param startTime
     * @param endTime
     * @param type
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/getUsedListNetbar", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getUsedListNetbar(HttpServletRequest request,
                                  @RequestParam(required = false) String netbarName,
                                  @RequestParam(required = false) String startTime,
                                  @RequestParam(required = false) String endTime,
                                        @RequestParam(required = false) Integer type ) {
        return iTbDeviceUsedService.getUsedListNetbar(request, netbarName,startTime,endTime,type,buildPage());
    }

}

