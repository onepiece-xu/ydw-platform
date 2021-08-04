package com.ydw.game.controller;


import com.ydw.game.controller.BaseController;
import com.ydw.game.model.db.TbTag;
import com.ydw.game.model.db.TbTagType;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.service.ITbTagService;
import com.ydw.game.service.ITbTagTypeService;


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
 * @author heao
 * @since 2020-06-29
 */
@CrossOrigin
@Controller
@RequestMapping("/tagType")
public class TbTagTypeController  extends BaseController {
    @Autowired
    private ITbTagTypeService iTbTagTypeService;

    @PostMapping(value = "/add")
    @ResponseBody
    public ResultInfo add(HttpServletRequest request, @RequestBody TbTagType body) {

        return iTbTagTypeService.add(request,body);

    }

    @PostMapping(value = "/updateTagType")
    @ResponseBody
    public ResultInfo updateTagType(HttpServletRequest request, @RequestBody TbTagType body) {

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

