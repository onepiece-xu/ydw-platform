package com.ydw.resource.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ydw.resource.dao.ClustersMapper;
import com.ydw.resource.dao.UserAppsMapper;
import com.ydw.resource.model.constant.Constant;
import com.ydw.resource.model.db.Clusters;
import com.ydw.resource.model.db.UserApps;
import com.ydw.resource.model.vo.AppVO;
import com.ydw.resource.service.IAppService;
import com.ydw.resource.utils.HttpUtil;
import com.ydw.resource.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xulh
 * @description: TODO
 * @date 2020/12/716:08
 */
@Service
public class AppServiceImpl implements IAppService {

    @Autowired
    private ClustersMapper clustersMapper;

    @Autowired
    private YdwClusterServiceImpl ydwClusterService;

    @Autowired
    private UserAppsMapper userAppsMapper;

    @Override
    public ResultInfo getApps(String identification) {
        List<AppVO> appVOS = userAppsMapper.getAppsByIdentification(identification);
        return  ResultInfo.success(appVOS);
    }

    @Override
    public ResultInfo issueApp(HashMap<String,String> parameter) {
        String appId = parameter.get("appId");
        List<Clusters> clustersByAppId = clustersMapper.getClustersByAppId(appId);
        for (Clusters clusters : clustersByAppId){
            Map<String,String> headers = ydwClusterService.buildHeader(clusters.getId());
            HttpUtil.doJsonPost(clusters.getApiUrl() + Constant.URL_EDGE_ISSUEDAPP, headers, parameter);
        }
        UpdateWrapper<UserApps> uw = new UpdateWrapper<UserApps>();
        uw.eq("id", appId);
        uw.set("status",5);
        userAppsMapper.update(null, uw);
        return  ResultInfo.success();
    }

    @Override
    public ResultInfo issuedApp(String appId, String clusterId) {
        //这里暂时认为一个边缘节点下发完成则认为整次下发动作已完成
        UpdateWrapper<UserApps> uw = new UpdateWrapper<UserApps>();
        uw.eq("id", appId);
        uw.set("status",6);
        userAppsMapper.update(null, uw);
        return  ResultInfo.success();
    }
}
