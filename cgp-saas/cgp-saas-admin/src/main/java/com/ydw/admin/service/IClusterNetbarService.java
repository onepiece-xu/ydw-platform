package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.db.ClusterNetbar;
import com.ydw.admin.model.vo.ClusterNetbarBind;
import com.ydw.admin.model.vo.ResultInfo;


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
