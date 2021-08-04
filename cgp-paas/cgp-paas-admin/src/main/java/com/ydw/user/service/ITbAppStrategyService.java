package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbAppStrategy;
import com.ydw.user.utils.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-04-29
 */
public interface ITbAppStrategyService extends IService<TbAppStrategy> {

    ResultInfo createStrategy(HttpServletRequest request, TbAppStrategy strategy);

    ResultInfo updateStrategy(HttpServletRequest request, TbAppStrategy strategy);

    ResultInfo getStrategyList(HttpServletRequest request, String name, Integer fps, Integer speed, Integer output, Integer video, String encode, String search, Page buildPage);

    ResultInfo bindStrategy(HttpServletRequest request, Integer strategyId, List<String> ids);

    ResultInfo getBindApps(HttpServletRequest request, Integer strategyId,String appName,String enterpriseName,Integer type, Page buildPage);

    ResultInfo getUnBindApps(HttpServletRequest request, Integer strategyId,String appName,String enterpriseName,Integer type, Page buildPage);

    ResultInfo UnbindStrategy(HttpServletRequest request, Integer strategyId, List<String> ids);

    ResultInfo deleteStrategy(HttpServletRequest request, List<Integer> ids);
}
