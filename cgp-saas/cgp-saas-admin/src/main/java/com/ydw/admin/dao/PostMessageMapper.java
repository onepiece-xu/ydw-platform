package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.admin.model.db.PostMessage;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hea
 * @since 2020-10-08
 */
public interface PostMessageMapper extends BaseMapper<PostMessage> {

    int getUnreadCount(String userId);
}
