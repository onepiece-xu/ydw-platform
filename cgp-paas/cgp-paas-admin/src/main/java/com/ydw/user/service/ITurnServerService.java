package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.model.db.TurnServer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.utils.ResultInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-08-22
 */
public interface ITurnServerService extends IService<TurnServer> {

    ResultInfo getSignalServerList(String body, Page buildPage);

    ResultInfo addTurnServer(TurnServer turnServer);

    ResultInfo updateTurnServer(TurnServer turnServer);

    ResultInfo delete(List<Integer> ids);

    ResultInfo bindList(Integer id, Page buildPage);

    ResultInfo bind(String body);

    ResultInfo unbindList(Integer id, Page buildPage);

    ResultInfo getInfo(Integer id);
}
