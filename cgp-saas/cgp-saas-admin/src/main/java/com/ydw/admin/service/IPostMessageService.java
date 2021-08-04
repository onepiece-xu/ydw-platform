package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.db.PostMessage;
import com.ydw.admin.model.vo.ResultInfo;


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


    ResultInfo getMessages(String userId, Page buildPage);

    ResultInfo info(String id);

    ResultInfo add(PostMessage postMessage);

    ResultInfo addALL(PostMessage postMessage);
}
