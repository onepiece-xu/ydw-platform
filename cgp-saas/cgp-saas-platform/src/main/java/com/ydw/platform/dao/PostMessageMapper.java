package com.ydw.platform.dao;

import com.ydw.platform.model.db.PostMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.platform.model.vo.UnreadCountVO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hea
 * @since 2020-10-08
 */
public interface PostMessageMapper extends BaseMapper<PostMessage> {

    UnreadCountVO getUnreadCountByUserId(String userId);

    int getUnreadCount(String userId);
}
