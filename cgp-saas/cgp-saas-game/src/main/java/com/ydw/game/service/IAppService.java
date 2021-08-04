package com.ydw.game.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.game.model.db.App;
import com.ydw.game.model.vo.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-08-05
 */
public interface IAppService extends IService<App> {

	ResultInfo getAllApps();

    ResultInfo getSyncApps(String body);

    ResultInfo getApps(String body, Page buildPage);
}
