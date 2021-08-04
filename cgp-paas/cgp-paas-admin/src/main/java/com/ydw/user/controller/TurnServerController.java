package com.ydw.user.controller;


import com.ydw.user.model.db.TurnServer;
import com.ydw.user.service.ITurnServerService;
import com.ydw.user.utils.ResultInfo;
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
@RequestMapping("/turnServer")
public class TurnServerController  extends BaseController{

    @Autowired
    private ITurnServerService iTurnServerService;

    @GetMapping("/getTurnServerList")
    @ResponseBody
    public ResultInfo getTurnServerList(@RequestParam(required = false)String body){

        return  iTurnServerService.getSignalServerList(body,buildPage());
    }
    @PostMapping("/addTurnServer")
    @ResponseBody
    public ResultInfo addTurnServer(@RequestBody TurnServer turnServer){

        return iTurnServerService.addTurnServer(turnServer);
    }

    @PostMapping("/updateTurnServer")
    @ResponseBody
    public ResultInfo updateTurnServer(@RequestBody TurnServer turnServer){

        return iTurnServerService.updateTurnServer(turnServer);
    }


    @PostMapping("/delete")
    @ResponseBody
    public ResultInfo delete(@RequestBody List<Integer> ids){

        return iTurnServerService.delete(ids);
    }

    @GetMapping("bindList")
    @ResponseBody
    public ResultInfo  bindList(@RequestParam (required = false) Integer id){

        return  iTurnServerService.bindList(id,buildPage());
    }

    @GetMapping("unbindList")
    @ResponseBody
    public ResultInfo  unbindList(@RequestParam (required = false) Integer id){

        return  iTurnServerService.unbindList(id,buildPage());
    }

    @PostMapping("/bind")
    @ResponseBody
    public ResultInfo bind (@RequestBody String body){
        return iTurnServerService.bind(body);
    }

    @GetMapping("getInfo/{id}")
    @ResponseBody
    public ResultInfo  getInfo(@PathVariable (required = false) Integer id){

        return  iTurnServerService.getInfo(id);
    }
}

