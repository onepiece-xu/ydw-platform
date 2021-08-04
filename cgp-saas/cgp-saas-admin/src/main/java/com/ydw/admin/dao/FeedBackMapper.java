package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.FeedBack;
import com.ydw.admin.model.vo.FeedBackVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hea
 * @since 2020-10-23
 */
public interface FeedBackMapper extends BaseMapper<FeedBack> {

    Page<FeedBackVO>  getFeedBackList(String search, Page buildPage);
}
