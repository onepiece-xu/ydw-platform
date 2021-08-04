package com.ydw.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydw.user.model.db.ClusterAppConfig;
import com.ydw.user.dao.ClusterAppConfigMapper;
import com.ydw.user.service.IClusterAppConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.utils.ResultInfo;
import com.ydw.user.utils.SequenceGenerator;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2021-05-17
 */
@Service
public class ClusterAppConfigServiceImpl extends ServiceImpl<ClusterAppConfigMapper, ClusterAppConfig> implements IClusterAppConfigService {

    @Override
    public ResultInfo getAppClusterInfo(String appId, String clusterId) {
        QueryWrapper<ClusterAppConfig> qw = new QueryWrapper<>();
        qw.eq("app_id", appId);
        qw.eq("cluster_id",clusterId);
        ClusterAppConfig one = getOne(qw);
        return ResultInfo.success(one);
    }

    @Override
    public ResultInfo bindAppCluster(ClusterAppConfig clusterAppConfig) {
        QueryWrapper<ClusterAppConfig> qw = new QueryWrapper<>();
        qw.eq("app_id", clusterAppConfig.getAppId());
        qw.eq("cluster_id",clusterAppConfig.getClusterId());
        ClusterAppConfig one = getOne(qw);
        if (one != null){
            one.setAccountPath(clusterAppConfig.getAccountPath());
            one.setCloseShell(clusterAppConfig.getCloseShell());
            one.setPackageFileName(clusterAppConfig.getPackageFileName());
            one.setPackageFilePath(clusterAppConfig.getPackageFilePath());
            one.setSavePath(clusterAppConfig.getSavePath());
            one.setStartShell(clusterAppConfig.getStartShell());
            updateById(one);
        }else{
            clusterAppConfig.setId(SequenceGenerator.sequence());
            save(clusterAppConfig);
        }
        return ResultInfo.success();
    }
}
