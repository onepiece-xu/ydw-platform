package com.ydw.user.controller;


import com.ydw.user.model.db.SignalServer;
import com.ydw.user.service.ISignalServerService;
import com.ydw.user.utils.ResultInfo;
import jdk.nashorn.internal.ir.ReturnNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-08-22
 */
@Controller
@RequestMapping("/signalServer")
public class SignalServerController extends BaseController {
    @Autowired
    private ISignalServerService iSignalServerService;



    @GetMapping("/getSignalServerList")
    @ResponseBody
    public ResultInfo getSignalServerList(@RequestParam(required = false)String body){

        return  iSignalServerService.getSignalServerList(body,buildPage());
    }
    @PostMapping("/addSignalServer")
    @ResponseBody
    public ResultInfo addSignalServer(@RequestBody SignalServer signalServer){

        return iSignalServerService.addSignalServer(signalServer);
    }

    @PostMapping("/updateSignalServer")
    @ResponseBody
    public ResultInfo updateSignalServer(@RequestBody SignalServer signalServer){

        return iSignalServerService.updateSignalServer(signalServer);
    }

    @PostMapping("/delete")
    @ResponseBody
    public ResultInfo delete(@RequestBody List<Integer> ids){

        return iSignalServerService.delete(ids);
    }
    @GetMapping("bindList")
    @ResponseBody
    public ResultInfo  bindList(@RequestParam (required = false) Integer id){

        return  iSignalServerService.bindList(id,buildPage());
    }

    @PostMapping("/bind")
    @ResponseBody
    public ResultInfo bind (@RequestBody String body){
        return iSignalServerService.bind(body);
    }

    @GetMapping("unbindList")
    @ResponseBody
    public ResultInfo  unbindList(@RequestParam (required = false) Integer id){

        return  iSignalServerService.unbindList(id,buildPage());
    }

    @GetMapping("getInfo/{id}")
    @ResponseBody
    public ResultInfo  getInfo(@PathVariable (required = false) Integer id){

        return  iSignalServerService.getInfo(id);
    }
}

