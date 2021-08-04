package com.ydw.admin.controller;


import com.ydw.admin.model.db.Event;
import com.ydw.admin.model.vo.EventVO;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xulh
 * @since 2021-01-26
 */
@Controller
@RequestMapping("/event")
public class EventController extends BaseController{

    @Autowired
    private IEventService iEventService;

    /**
     * 添加活动
     */
    @PostMapping("/addEvent")
    public ResultInfo addEvent(HttpServletRequest request,Event event){
        return  iEventService.addEvent(request,event);
    }

    /**
     * 编辑活动
     */
    @PostMapping("/updateEvent")
    public ResultInfo updateEvent(HttpServletRequest request, Event event){
        return iEventService.updateEvent(request,event);
    }
    /**
     * 删除活动
     */
    @PostMapping("/deleteEvent")
    public ResultInfo deleteEvent(@RequestBody List<Integer> ids){
        return iEventService.deleteEvent(ids);
    }
    /**
     * 列表
     */
    @GetMapping("/eventsList")
    public ResultInfo eventsList(@RequestParam(required = false) String search){
        return iEventService.eventsList(search,buildPage());
    }

    /**
     * 启用禁用活动
     */
    @PostMapping("/enable")
    public ResultInfo enable(@RequestBody EventVO eventVO){
        return iEventService.enable(eventVO);
    }
}


