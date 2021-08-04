package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.FeedBack;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.vo.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hea
 * @since 2020-10-23
 */
public interface IFeedBackService extends IService<FeedBack> {

    ResultInfo commitFeedBack(FeedBack feedBack);

    ResultInfo getFeedBackList(String search, Page buildPage);
}
