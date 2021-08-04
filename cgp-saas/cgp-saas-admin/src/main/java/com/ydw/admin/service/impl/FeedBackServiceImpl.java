package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.dao.FeedBackMapper;
import com.ydw.admin.model.db.FeedBack;
import com.ydw.admin.model.vo.FeedBackVO;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IFeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hea
 * @since 2020-10-23
 */
@Service
public class FeedBackServiceImpl extends ServiceImpl<FeedBackMapper, FeedBack> implements IFeedBackService {

    @Autowired
    private FeedBackMapper feedBackMapper;
    @Override
    public ResultInfo commitFeedBack(FeedBack feedBack) {
        feedBack.setValid(true);
        feedBackMapper.insert(feedBack);
        return ResultInfo.success("提交成功，感谢反馈！");
    }

    @Override
    public ResultInfo getFeedBackList(String search, Page buildPage) {
//        QueryWrapper<FeedBack> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("valid",1);
//        queryWrapper.eq("")
//        Page page = feedBackMapper.selectPage(buildPage, queryWrapper);
        Page<FeedBackVO> feedBackList = feedBackMapper.getFeedBackList(search, buildPage);
        return ResultInfo.success(feedBackList);
    }
}
