package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.db.PostMessage;
import com.ydw.platform.model.vo.ResultInfo;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author hea
 * @since 2020-10-08
 */
public interface IPostMessageService extends IService<PostMessage> {


    ResultInfo getMessages(String userId,Integer type, Page buildPage);

    ResultInfo info(String id);

    ResultInfo add(Map<String, Object> map);

    ResultInfo getUnreadCountByUserId(String userId);

    ResultInfo allRead(String userId,Integer type);
}
