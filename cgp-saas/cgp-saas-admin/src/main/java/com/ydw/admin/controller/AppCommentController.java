package com.ydw.admin.controller;


import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IAppCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xulh
 * @since 2021-04-21
 */
@Controller
@RequestMapping("/appComment")
public class AppCommentController extends BaseController{

    @Autowired
    private IAppCommentService iAppCommentService;

    @GetMapping("/getAppCommentList")
    public ResultInfo getAppCommentList(@RequestParam(required = false)String search,@RequestParam(required = false)Integer reported){
        return  iAppCommentService.getAppCommentList(search,reported,buildPage());
    }

    /**
     * 删除评论
     */
    @PostMapping("/deleteAppComments")
    public ResultInfo deleteAppComments(@RequestBody List<Integer> ids){
        return  iAppCommentService.deleteAppComments(ids);
    }
    /**
     * 审核通过评论
     */
    @PostMapping("/approvedAppComments")
    public ResultInfo approvedAppComments(@RequestBody List<Integer> ids){
        return  iAppCommentService.approvedAppComments(ids);
    }

    /**
     * 审核通过评论
     */
    @PostMapping("/refuseAppComments")
    public ResultInfo refuseAppComments(@RequestBody List<Integer> ids){
        return  iAppCommentService.refuseAppComments(ids);
    }

}

