package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.db.Event;

import com.ydw.platform.model.vo.ResultInfo;



/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-01-26
 */
public interface IEventService extends IService<Event> {

    ResultInfo eventsList(String search, Integer  type,Integer platform,Page buildPage);
}
