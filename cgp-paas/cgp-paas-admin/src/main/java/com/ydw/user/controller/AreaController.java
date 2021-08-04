package com.ydw.user.controller;


import com.ydw.user.model.db.Area;
import com.ydw.user.service.IAreaService;
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
 * @since 2020-09-04
 */
@Controller
@RequestMapping("/area")
public class AreaController extends  BaseController {
    @Autowired
    private IAreaService iAreaService;

    @GetMapping("/getAreaList")
    @ResponseBody
    public ResultInfo getAreaList(){
        return iAreaService.getAreaList(buildPage());
    }
    @PostMapping("/addArea")
    @ResponseBody
    public ResultInfo addArea(@RequestBody Area area){
        return  iAreaService.addArea(area);
    }

    @PostMapping("/updateArea")
    @ResponseBody
    public ResultInfo updateArea(@RequestBody Area area){
        return  iAreaService.updateArea(area);
    }
    @PostMapping("/deleteArea")
    @ResponseBody
    public ResultInfo deleteArea(@RequestBody List<Integer> ids){
        return  iAreaService.deleteArea(ids);
    }
    /**
     * 绑定集群
     */
    @PostMapping("/bindArea")
    @ResponseBody
    public ResultInfo bind(@RequestBody String body){
        return iAreaService.bind(body);
    }

    /**
     * 获取未绑定列表
     */
    @GetMapping("/getUnbindList")
    @ResponseBody
    public ResultInfo getUnbindList(@RequestParam Integer id){
        return iAreaService.getUnbindList(id,buildPage());
    }

    /**
     * 绑定集群列表
     * @param id
     * @return
     */
    @GetMapping("/getBindList")
    @ResponseBody
    public ResultInfo getBindList(@RequestParam Integer id){
        return iAreaService.getBindList(id,buildPage());
    }
}

