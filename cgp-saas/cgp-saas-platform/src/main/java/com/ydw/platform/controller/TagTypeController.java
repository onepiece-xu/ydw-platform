package com.ydw.platform.controller;


import com.ydw.platform.model.db.TagType;
import com.ydw.platform.service.ITagTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ydw.platform.model.vo.ResultInfo;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author heao
 * @since 2020-06-29
 */
@Controller
@RequestMapping("/tagType")
public class TagTypeController  extends BaseController {
    @Autowired
    private ITagTypeService iTbTagTypeService;

    @PostMapping(value = "/add")
    @ResponseBody
    public ResultInfo add(HttpServletRequest request, @RequestBody TagType body) {

        return iTbTagTypeService.add(request,body);

    }

    @PostMapping(value = "/updateTagType")
    @ResponseBody
    public ResultInfo updateTagType(HttpServletRequest request, @RequestBody TagType body) {

        return iTbTagTypeService.updateTagType(request,body);

    }
    @PostMapping(value = "/deleteTagType")
    @ResponseBody
    public ResultInfo deleteTagType(HttpServletRequest request, @RequestBody List<Integer> ids) {

        return iTbTagTypeService.deleteTagType(request,ids);

    }

    @GetMapping(value = "/getTagTypeList")
    @ResponseBody
    public ResultInfo getTagTypeList(HttpServletRequest request, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String search) {

        return iTbTagTypeService.getTagTypeList(request,buildPage(),search);

    }



    @GetMapping(value = "/tagTypeList")
    @ResponseBody
    public ResultInfo tagList(HttpServletRequest request, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize, @RequestParam(required = false) String search) {

        return iTbTagTypeService.tagList(request,buildPage(),search);

    }
}

