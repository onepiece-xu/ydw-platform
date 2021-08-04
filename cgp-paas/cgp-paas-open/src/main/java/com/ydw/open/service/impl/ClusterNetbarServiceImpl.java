package com.ydw.open.service.impl;

import com.alibaba.fastjson.JSON;
import com.ydw.open.model.db.ClusterNetbar;
import com.ydw.open.dao.ClusterNetbarMapper;
import com.ydw.open.model.vo.ClusterNetbarBind;
import com.ydw.open.service.IClusterNetbarService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.open.utils.HttpUtil;
import com.ydw.open.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-12-11
 */
@Service
public class ClusterNetbarServiceImpl extends ServiceImpl<ClusterNetbarMapper, ClusterNetbar> implements IClusterNetbarService {

//    @Autowired
//    private ClusterNetbarMapper clusterNetbarMapper;
    @Autowired
    private NetBarInfoServiceImpl netBarInfoService;
    @Value("${saas.url}")
    private String saasUrl;

    @Override
    public ResultInfo clusterNetbarBind(ClusterNetbarBind clusterNetbarBind) {
        String clusterId = clusterNetbarBind.getClusterId();
        String netbarId = clusterNetbarBind.getNetbarId();
        String identification = "91110108MA01QB042G";
        String saasToken = netBarInfoService.getSaasToken(identification);
        String url = saasUrl + "cgp-saas-admin/clusterNetbar/bind";
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", saasToken);
        HashMap<String,Object> params = new HashMap<>();
        params.put("clusterId",clusterId);
        params.put("netbarId",netbarId);
        String result = HttpUtil.doJsonPost(url, headers, params);
        ResultInfo resultInfo = JSON.parseObject(result, ResultInfo.class);
       /* String clusterId = clusterNetbarBind.getClusterId();
        if (!clusterId.equals("")) {
            QueryWrapper<ClusterNetbar> wrapper0 = new QueryWrapper<>();
            wrapper0.eq("cluster_id", clusterId);
//            wrapper.eq("cluster_id",clusterId);
            ClusterNetbar clusterNetbar0 = clusterNetbarMapper.selectOne(wrapper0);
            if (null != clusterNetbar0) {
                return ResultInfo.fail("集群已经被绑定，绑定失败！");
            }
        }
        String netbarId = clusterNetbarBind.getNetbarId();
        if (!StringUtil.nullOrEmpty(clusterId)) {
            QueryWrapper<ClusterNetbar> wrapper = new QueryWrapper<>();
            wrapper.eq("netbar_id", netbarId);
//            wrapper.eq("cluster_id",clusterId);
            ClusterNetbar clusterNetbar = clusterNetbarMapper.selectOne(wrapper);
            if (null == clusterNetbar) {
                ClusterNetbar netbar = new ClusterNetbar();
                netbar.setClusterId(clusterId);
                netbar.setNetbarId(netbarId);
                clusterNetbarMapper.insert(netbar);
            } else {
                String clusterNetbarClusterId = clusterNetbar.getClusterId();
                if (StringUtil.nullOrEmpty(clusterNetbarClusterId)) {
                    clusterNetbar.setClusterId(clusterId);
                    clusterNetbarMapper.updateById(clusterNetbar);
                }
            }

        } else {
            QueryWrapper<ClusterNetbar> wrapper = new QueryWrapper<>();
            wrapper.eq("netbar_id", netbarId);
//            wrapper.eq("cluster_id",clusterId);
            ClusterNetbar clusterNetbar = clusterNetbarMapper.selectOne(wrapper);
            if (null != clusterNetbar) {
                clusterNetbar.setClusterId(clusterId);
                clusterNetbarMapper.updateById(clusterNetbar);
            }

        }*/
        return resultInfo;
    }
}
