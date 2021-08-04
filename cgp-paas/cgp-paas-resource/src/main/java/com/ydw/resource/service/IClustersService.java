package com.ydw.resource.service;

import com.ydw.resource.model.db.Clusters;
import com.ydw.resource.utils.ResultInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-08-24
 */
public interface IClustersService extends IService<Clusters> {

	ResultInfo getClusters(String identification, String appId);

    ResultInfo updateExternalIp(String oldIp, String newIp);
}
