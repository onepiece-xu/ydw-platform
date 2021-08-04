package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.Event;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.vo.EventVO;
import com.ydw.admin.model.vo.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-01-26
 */
public interface IEventService extends IService<Event> {

    ResultInfo addEvent(HttpServletRequest request,Event event);

    ResultInfo updateEvent(HttpServletRequest request,Event event);

    ResultInfo deleteEvent(List<Integer> ids);

    ResultInfo eventsList(String search,Page buildPage);

    ResultInfo enable(EventVO eventVO);
}
