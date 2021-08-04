package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbKeyConfig;
import com.ydw.user.utils.ResultInfo;

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
public interface ITbKeyConfigService extends IService<TbKeyConfig> {


     ResultInfo getConfigList(HttpServletRequest request, String body, Page buildPage);

    ResultInfo addConfig(HttpServletRequest request, TbKeyConfig tbKeyConfig);

    ResultInfo updateConfig(HttpServletRequest request, TbKeyConfig tbKeyConfig);

    ResultInfo deleteConfig(HttpServletRequest request, List<Integer> ids);
}
