package com.ydw.user.controller;


import com.ydw.user.model.db.TbAppStrategy;
import com.ydw.user.service.ITbAppStrategyService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-04-29
 */
@Controller
@RequestMapping("/v1/appStrategy")
public class TbAppStrategyController  extends BaseController{
    @Autowired
    private ITbAppStrategyService iTbAppStrategyService;


    @RequestMapping(value = "/createStrategy", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo createStrategy(HttpServletRequest request, @RequestBody TbAppStrategy strategy) {

        return iTbAppStrategyService.createStrategy(request, strategy);
    }

    @RequestMapping(value = "/updateStrategy", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo updateStrategy(HttpServletRequest request, @RequestBody TbAppStrategy strategy) {

        return iTbAppStrategyService.updateStrategy(request, strategy);
    }


    @RequestMapping(value = "/delStrategy", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo delStrategy(HttpServletRequest request, @RequestBody List<Integer> ids) {

        return iTbAppStrategyService.deleteStrategy(request, ids);
    }

    @RequestMapping(value = "/getStrategyList", method = RequestMethod.GET)
    @ResponseBody
    public ResultInfo getStrategyList(HttpServletRequest request, @RequestParam(required = false) String name,
                                      @RequestParam(required = false) Integer fps,
                                      @RequestParam(required = false) Integer speed,
                                      @RequestParam(required = false) Integer output,
                                      @RequestParam(required = false) Integer video,
                                      @RequestParam(required = false) String encode,
                                      @RequestParam(required = false) String search,
                                      @RequestParam(required = false) Integer pageNum,
                                      @RequestParam(required = false) Integer pageSize) {

        return iTbAppStrategyService.getStrategyList(request, name, fps, speed, output, video, encode,search, buildPage());
    }

    @RequestMapping(value = "/bindStrategy/{strategyId}", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo bindStrategy(HttpServletRequest request, @PathVariable Integer strategyId, @RequestBody List<String> ids) {

        return iTbAppStrategyService.bindStrategy(request, strategyId, ids);
    }

    @RequestMapping(value = "/UnbindStrategy/{strategyId}", method = RequestMethod.POST)
    @ResponseBody
    public ResultInfo UnbindStrategy(HttpServletRequest request, @PathVariable Integer strategyId, @RequestBody List<String> ids) {

        return iTbAppStrategyService.UnbindStrategy(request, strategyId, ids);
    }

    @GetMapping(value = "/getBindApps/{strategyId}")
    @ResponseBody
    public ResultInfo getBindApps(HttpServletRequest request, @PathVariable Integer strategyId, @RequestParam(required = false) String appName,
                                  @RequestParam(required = false) String enterpriseName,
                                  @RequestParam(required = false) Integer type,
                                  @RequestParam(required = false) Integer pageNum,
                                  @RequestParam(required = false) Integer pageSize) {


        return iTbAppStrategyService.getBindApps(request, strategyId, appName, enterpriseName, type, buildPage());
    }

    @GetMapping(value = "/getUnBindApps/{strategyId}")
    @ResponseBody
    public ResultInfo getUnBindApps(HttpServletRequest request, @PathVariable Integer strategyId, @RequestParam(required = false) String appName,
                                    @RequestParam(required = false) String userName,
                                    @RequestParam(required = false) Integer type,
                                    @RequestParam(required = false) Integer pageNum,
                                    @RequestParam(required = false) Integer pageSize) {

        return iTbAppStrategyService.getUnBindApps(request, strategyId, appName, userName, type, buildPage());
    }
}

