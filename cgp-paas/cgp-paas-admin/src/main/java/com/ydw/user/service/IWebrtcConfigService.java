package com.ydw.user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.model.db.TbWebrtcConfig;
import com.ydw.user.utils.ResultInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-08-07
 */
public interface IWebrtcConfigService extends IService<TbWebrtcConfig> {

    ResultInfo getConfigList(String body, Page buildPage);

    ResultInfo addConfig(TbWebrtcConfig webrtcConfig);

    ResultInfo updateConfig(TbWebrtcConfig webrtcConfig);

    ResultInfo deleteConfig(List<Integer> ids);
}
