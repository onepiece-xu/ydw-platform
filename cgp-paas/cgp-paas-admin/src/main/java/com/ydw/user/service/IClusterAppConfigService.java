package com.ydw.user.service;

import com.ydw.user.model.db.ClusterAppConfig;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.user.utils.ResultInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2021-05-17
 */
public interface IClusterAppConfigService extends IService<ClusterAppConfig> {

    ResultInfo getAppClusterInfo(String appId, String clusterId);

    ResultInfo bindAppCluster(ClusterAppConfig clusterAppConfig);
}
