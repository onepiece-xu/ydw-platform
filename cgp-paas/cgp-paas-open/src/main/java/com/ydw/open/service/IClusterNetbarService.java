package com.ydw.open.service;

import com.ydw.open.model.db.ClusterNetbar;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.open.model.vo.ClusterNetbarBind;
import com.ydw.open.utils.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2020-12-11
 */
public interface IClusterNetbarService extends IService<ClusterNetbar> {

    ResultInfo clusterNetbarBind(ClusterNetbarBind clusterNetbarBind);
}
