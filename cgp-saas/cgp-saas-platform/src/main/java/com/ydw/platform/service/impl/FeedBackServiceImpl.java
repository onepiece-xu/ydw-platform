package com.ydw.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.FeedBack;
import com.ydw.platform.dao.FeedBackMapper;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IFeedBackService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        feedBack.setTime(new Date());
        feedBack.setValid(true);
        feedBackMapper.insert(feedBack);
        return ResultInfo.success("提交成功，感谢反馈！");
    }

    @Override
    public ResultInfo getFeedBackList(String search, Page buildPage) {
        QueryWrapper<FeedBack> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("valid",1);
        Page page = feedBackMapper.selectPage(buildPage, queryWrapper);
        return ResultInfo.success(page);
    }
}
