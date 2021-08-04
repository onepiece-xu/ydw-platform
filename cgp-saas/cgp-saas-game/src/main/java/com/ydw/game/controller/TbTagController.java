package com.ydw.game.controller;


import com.ydw.game.model.db.TbTag;
import com.ydw.game.model.vo.ResultInfo;
import com.ydw.game.service.ITbTagService;

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
 * @since 2020-06-29
 */
@CrossOrigin
@Controller
@RequestMapping("/tag")
public class TbTagController  extends BaseController {
    @Autowired
    private ITbTagService iTbTagService;

    /**
     * 云游戏页面获取标签列表
     * @param request
     * @return
     */
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



    @PostMapping(value = "/add")
    @ResponseBody
    public ResultInfo add(HttpServletRequest request, @RequestBody TbTag body) {

        return iTbTagService.add(request,body);

    }
    @PostMapping(value = "/updateTag")
    @ResponseBody
    public ResultInfo updateTag(HttpServletRequest request, @RequestBody TbTag body) {

        return iTbTagService.updateTag(request,body);

    }

    @PostMapping(value = "/deleteTags")
    @ResponseBody
    public ResultInfo deleteTags(HttpServletRequest request, @RequestBody List<Integer> ids) {

        return iTbTagService.deleteTags(request,ids);

    }

    @GetMapping(value = "/getTags")
    @ResponseBody
    public ResultInfo getTags(HttpServletRequest request, @RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize,@RequestParam(required = false) String search) {

        return iTbTagService.getTags(request,buildPage(),search);

    }
    /**
     * 给应用绑定标签
     */
    @PostMapping(value = "/bindTags")
    @ResponseBody
    public ResultInfo bindTags(HttpServletRequest request, @RequestBody String body) {

        return iTbTagService.bindTags(request,body);

    }

    /**
     * 获取标签已绑定应用
     */
    @GetMapping(value = "/getBindApps")
    @ResponseBody
    public ResultInfo getBindApps(HttpServletRequest request, @RequestParam(required = false) Integer tagId,@RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize) {

        return iTbTagService.getBindApps(request,tagId,  buildPage());

    }

    /**
     * 获取标签未绑定应用
     * @param request
     * @param tagId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/getUnBindApps")
    @ResponseBody
    public ResultInfo getUnBindApps(HttpServletRequest request, @RequestParam(required = false) Integer tagId,@RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize) {
        return iTbTagService.getUnBindApps(request,tagId,buildPage());

    }


    /**
     * 根据标签类型分组查询标签列表
     */
    @GetMapping(value = "/getTagsByType")
    @ResponseBody
    public ResultInfo getTagsByType(HttpServletRequest request) {

        return iTbTagService.getTagsByType(request);

    }




}

