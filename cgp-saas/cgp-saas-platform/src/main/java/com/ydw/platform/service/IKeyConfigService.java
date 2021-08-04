package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.platform.model.db.KeyConfig;
import com.ydw.platform.model.vo.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-07-27
 */
public interface IKeyConfigService  {


     ResultInfo getConfigList(HttpServletRequest request, String body, Page buildPage);
//
    ResultInfo addConfig(HttpServletRequest request, KeyConfig tbKeyConfig);

    ResultInfo updateConfig(HttpServletRequest request, KeyConfig tbKeyConfig);

    ResultInfo deleteConfig(HttpServletRequest request, List<Integer> ids);
}
