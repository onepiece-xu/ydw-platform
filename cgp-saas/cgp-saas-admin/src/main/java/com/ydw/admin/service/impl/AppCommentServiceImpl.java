package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.AppComment;
import com.ydw.admin.dao.AppCommentMapper;
import com.ydw.admin.model.vo.AppCommentListVO;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IAppCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-04-21
 */
@Service
public class AppCommentServiceImpl extends ServiceImpl<AppCommentMapper, AppComment> implements IAppCommentService {
    @Autowired
    private AppCommentMapper appCommentMapper;
    @Override
    public ResultInfo getAppCommentList(String search,Integer reported, Page buildPage) {
        Page<AppCommentListVO> appCommentList = appCommentMapper.getAppCommentList(search, reported,buildPage);
        return ResultInfo.success(appCommentList);
    }

    @Override
    public ResultInfo deleteAppComments(List<Integer> ids) {
        for(Integer i:ids){
            AppComment appComment = new AppComment();
            appComment.setId(i);
            appComment.setStatus(0);
            appCommentMapper.updateById(appComment);
        }
        return  ResultInfo.success();
    }

    @Override
    public ResultInfo approvedAppComments(List<Integer> ids) {
        for(Integer i:ids){
            AppComment appComment = new AppComment();
            appComment.setId(i);
            appComment.setApproved(1);
            appCommentMapper.updateById(appComment);
        }
        return  ResultInfo.success();
    }

    @Override
    public ResultInfo refuseAppComments(List<Integer> ids) {
        for(Integer i:ids){
            AppComment appComment = new AppComment();
            appComment.setId(i);
            appComment.setApproved(2);
            appCommentMapper.updateById(appComment);
        }
        return  ResultInfo.success();
    }

}
