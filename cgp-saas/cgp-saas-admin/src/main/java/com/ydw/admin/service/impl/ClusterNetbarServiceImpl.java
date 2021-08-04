package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.dao.ClusterNetbarMapper;
import com.ydw.admin.model.db.ClusterNetbar;
import com.ydw.admin.model.vo.ClusterNetbarBind;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IClusterNetbarService;

import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Autowired
    private ClusterNetbarMapper clusterNetbarMapper;

    @Override
    public ResultInfo clusterNetbarBind(ClusterNetbarBind clusterNetbarBind) {
        String clusterId = clusterNetbarBind.getClusterId();
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
        if (!StringUtil.isNullOrEmpty(clusterId)) {
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
                if (StringUtil.isNullOrEmpty(clusterNetbarClusterId)) {
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
        }
        return ResultInfo.success("操作成功 ！");
    }
}
