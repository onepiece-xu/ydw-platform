package com.ydw.platform.controller;

import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.INetBarInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-12-09
 */
@RestController
@RequestMapping("/netBarInfo")
public class NetBarInfoController extends BaseController {

    @Autowired
    private INetBarInfoService iNetBarInfoService;


    @GetMapping("/getNetBarList")
    @ResponseBody
    public ResultInfo getNetBarList( HttpServletRequest request,@RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "province", required = false) String province,
                                    @RequestParam(value = "city", required = false) String city,
                                    @RequestParam(value = "district", required = false) String district){
        return  iNetBarInfoService.getNetBarList(request,name,province,city,district,buildPage());
    }

    @GetMapping("/getNetBarInfo/{id}")
    @ResponseBody
    public ResultInfo getNetBarInfo( @PathVariable String id){
        return  iNetBarInfoService.getNetBarInfo(id);
    }


    /**
     * 获取所有网吧列表信息
     * @param request
     * @return
     */
    @GetMapping("/getAllNetBarList")
    @ResponseBody
    public ResultInfo getAllNetBarList(HttpServletRequest request){
        return  iNetBarInfoService.getAllNetBarList(request,buildPage());
    }

    /**
     * 获取所有网吧设备状态
     */
    @GetMapping("/getAllNetBarStatus")
    @ResponseBody
    public ResultInfo getAllNetBarStatus(HttpServletRequest request){
        return  iNetBarInfoService.getAllNetBarStatus(request,buildPage());
    }

    /**
     * 安卓客户端获取网吧展示列表
     */
    @GetMapping("/getNetBarListInfo")
    @ResponseBody
    public ResultInfo getNetBarListAndroid( HttpServletRequest request,@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "province", required = false) String province,
                                     @RequestParam(value = "city", required = false) String city,
                                     @RequestParam(value = "district", required = false) String district){
        return  iNetBarInfoService.getNetBarListAndroid(request,name,province,city,district,buildPage());
    }

    /**
     * 根据网吧获取网吧集群状态
     */
    @GetMapping("/getNetBarStatus")
    @ResponseBody
    public ResultInfo getNetBarStatus( HttpServletRequest request,@RequestParam(value = "name", required = false) String name,
                                            @RequestParam(value = "province", required = false) String province,
                                            @RequestParam(value = "city", required = false) String city,
                                            @RequestParam(value = "district", required = false) String district){
        return  iNetBarInfoService.getNetBarStatus(request,name,province,city,district,buildPage());
    }
}

