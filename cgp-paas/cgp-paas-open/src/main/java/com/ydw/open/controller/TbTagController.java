package com.ydw.open.controller;

import com.ydw.open.service.ITbTagService;
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
 * @since 2020-06-29
 */
@Controller
@RequestMapping("/tag")
public class TbTagController  extends BaseController{
    @Autowired
    private ITbTagService iTbTagService;

    @GetMapping(value = "/getTagList")
    @ResponseBody
    public ResultInfo getTagList(HttpServletRequest request) {

        return iTbTagService.getTagList(request);

    }

    @PostMapping(value = "/getAppListByTag")
    @ResponseBody
    public ResultInfo getAppListByTag(HttpServletRequest request, @RequestBody String body) {
        return iTbTagService.getAppListByTag(request,body);
    }

}

