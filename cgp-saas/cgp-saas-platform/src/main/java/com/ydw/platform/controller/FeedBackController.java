package com.ydw.platform.controller;


import com.ydw.platform.model.db.FeedBack;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IFeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hea
 * @since 2020-10-23
 */
@Controller
@RequestMapping("/feedBack")
public class FeedBackController extends BaseController{

    @Autowired
    private IFeedBackService iFeedBackService;

    @PostMapping("/commit")
    public ResultInfo commitFeedBack(@RequestBody FeedBack feedBack){

        return iFeedBackService.commitFeedBack(feedBack);
    }
    @GetMapping("/getFeedBackList")
    public ResultInfo getFeedBackList(@RequestParam(required = false) String search){
        return  iFeedBackService.getFeedBackList(search,buildPage());
    }
}

