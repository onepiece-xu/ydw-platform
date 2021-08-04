package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.AppComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.vo.ResultInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-04-21
 */
public interface IAppCommentService extends IService<AppComment> {

    ResultInfo addComment(HttpServletRequest request,AppComment appComment);

    ResultInfo deleteComment(AppComment appComment);

    ResultInfo reportComment(AppComment appComment);

    ResultInfo likeComment(HttpServletRequest request,AppComment appComment);

    ResultInfo getCommentList(HttpServletRequest request,String appId, Page page);
}
