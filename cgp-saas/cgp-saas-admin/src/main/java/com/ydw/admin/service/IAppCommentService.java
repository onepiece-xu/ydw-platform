package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.AppComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.vo.ResultInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-04-21
 */
public interface IAppCommentService extends IService<AppComment> {

    ResultInfo getAppCommentList(String search,Integer reported, Page buildPage);

    ResultInfo deleteAppComments(List<Integer> ids);

    ResultInfo approvedAppComments(List<Integer> ids);

    ResultInfo refuseAppComments(List<Integer> ids);
}
