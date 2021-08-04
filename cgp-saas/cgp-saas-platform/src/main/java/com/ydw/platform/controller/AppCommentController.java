package com.ydw.platform.controller;


import com.ydw.platform.model.db.AppComment;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IAppCommentService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

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
    /**
     * 用户评论留言
     */
    @PostMapping("/addComment")
    public ResultInfo addComment(HttpServletRequest request, @RequestBody AppComment appComment){
        return iAppCommentService.addComment(request,appComment);
    }
    /**
     * 用户删除评论
     */
    @PostMapping("/deleteComment")
    public ResultInfo deleteComment(@RequestBody AppComment appComment){
        return iAppCommentService.deleteComment(appComment);
    }
    /**
     *用户举报接口
     */
    @PostMapping("/report")
    public ResultInfo reportComment(@RequestBody AppComment appComment){
        return iAppCommentService.reportComment(appComment);
    }
    /**
     *用户点赞 取消点赞
     */
    @PostMapping("/like")
    public ResultInfo likeComment(HttpServletRequest request,@RequestBody AppComment appComment){
        return iAppCommentService.likeComment(request,appComment);
    }

    /**
     * 展示app下评论列表
     */
    @GetMapping("/getCommentList")
    public ResultInfo getCommentList(HttpServletRequest request,@Param("appId")String appId){
        return iAppCommentService.getCommentList(request,appId,buildPage());
    }
}

