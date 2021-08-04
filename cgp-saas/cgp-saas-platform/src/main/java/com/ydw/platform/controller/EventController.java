package com.ydw.platform.controller;



import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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
     * 列表
     */
    @GetMapping("/eventsList")
    public ResultInfo eventsList(@RequestParam(required = false) String search,@RequestParam(required = false) Integer type
            ,@RequestParam(required = false) Integer platform){
        return iEventService.eventsList(search,type,platform,buildPage());
    }

}


