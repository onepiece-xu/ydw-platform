package com.ydw.user.controller;


import com.ydw.user.model.db.TbDeviceBase;
import com.ydw.user.service.ITbDeviceBaseService;
import com.ydw.user.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-07-24
 */
@Controller
@RequestMapping("/deviceBase")
public class TbDeviceBaseController extends BaseController {

    @Autowired
    private ITbDeviceBaseService iTbDeviceBaseService;

    @GetMapping(value= "/getBaseList")
    @ResponseBody
    public ResultInfo getBaseList(HttpServletRequest request, @RequestParam(required = false) String search){

        return iTbDeviceBaseService.getBaseList(request,search,buildPage());
    }


    @PostMapping(value= "/addDeviceBase")
    @ResponseBody
    public ResultInfo addDeviceBase(HttpServletRequest request, @RequestBody TbDeviceBase tbDeviceBase){

        return iTbDeviceBaseService.addDeviceBase(request,tbDeviceBase);
    }

    @PostMapping(value= "/updateDeviceBase")
    @ResponseBody
    public ResultInfo updateDeviceBase(HttpServletRequest request, @RequestBody TbDeviceBase tbDeviceBase){

        return iTbDeviceBaseService.updateDeviceBase(request,tbDeviceBase);
    }
    @PostMapping(value= "/deleteDeviceBase")
    @ResponseBody
    public ResultInfo deleteDeviceBase(HttpServletRequest request, @RequestBody List<Integer> ids ){

        return iTbDeviceBaseService.deleteDeviceBase(request,ids);
    }

    @GetMapping(value= "/getBaseById/{id}")
    @ResponseBody
    public ResultInfo getBaseById(HttpServletRequest request, @PathVariable(required = false) Integer id){



        return iTbDeviceBaseService.getBaseById(request,id);
    }

}
