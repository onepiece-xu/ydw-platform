package com.ydw.open.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.open.dao.*;
import com.ydw.open.model.db.*;
import com.ydw.open.service.ITbClustersService;
import com.ydw.open.service.IYdwAuthenticationService;
import com.ydw.open.utils.ResultInfo;
import com.ydw.open.utils.SequenceGenerator;
import com.ydw.open.utils.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-05-13
 */
@Service
public class TbClustersServiceImpl extends ServiceImpl<TbClustersMapper, TbClusters> implements ITbClustersService {

    @Autowired
    private TbClustersMapper tbClustersMapper;

    @Autowired
    private TbDeviceGroupMapper tbDeviceGroupMapper;

    @Autowired
    private TbDevicesMapper tbDevicesMapper;

    @Autowired
    private ClusterConfigMapper clusterConfigMapper;

    @Autowired
    private TurnServerMapper turnServerMapper;
    @Autowired
    private SignalServerMapper signalServerMapper;

    @Autowired
    private IYdwAuthenticationService iYdwAuthenticationService;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public ResultInfo createCluster(HttpServletRequest request, TbClusters cluster) {
        cluster.setId(SequenceGenerator.sequence());
        cluster.setCreateTime(new Date());
        cluster.setSchStatus(true);
        cluster.setValid(true);
        cluster.setDeviceNum(0);
        tbClustersMapper.insert(cluster);
        logger.info(cluster.getName()+"创建成功！");
        return ResultInfo.success();
    }

    @Override
    public ResultInfo updateCluster(HttpServletRequest request, TbClusters cluster) {
        cluster.setUpdateTime(new Date());
        tbClustersMapper.updateById(cluster);
        logger.info(cluster.getName()+"编辑成功！");
        return ResultInfo.success("Update Success");
    }

    @Override
    public ResultInfo deleteClusters(HttpServletRequest request, List<String> ids) {
        String result="";
        TbClusters clusters = new TbClusters();
        for (String id : ids) {
//            List<TbDeviceGroup> groupByClusterId = tbDeviceGroupMapper.getGroupByClusterId(id);
            List<TbDevices> devicesByClusterId = tbDevicesMapper.getDevicesByClusterId(id);
            if(devicesByClusterId.size()>0){
                TbClusters tbClusters = tbClustersMapper.selectById(id);
                result=result+tbClusters.getName()+",";

            }else{
                clusters.setId(id);
                //软删除
                clusters.setValid(false);
                clusters.setUpdateTime(new Date());
                tbClustersMapper.updateById(clusters);
            }

        }
        if(result!="") {
            result = result + "存在未删除的设备,删除失败!";
            logger.error(result);
            return ResultInfo.fail(result);
        }

        return  ResultInfo.success();
    }

    @Override
    public ResultInfo getClusters(HttpServletRequest request, String name, Integer type, Integer isLocal, String description, String apiUrl, String accessIp, Integer schStatus, String search,Page buildPage) {
//        if (null != pageNum && null != pageSize) {
//            PageHelper.startPage(pageNum, pageSize);
//        } else {
//            PageHelper.startPage(1, 10);
//        }
        String token = request.getHeader("Authorization");
        ResultInfo id = iYdwAuthenticationService.getIdentification(token);
        String identification = id.getMsg();
        Page<TbClusters> list =tbClustersMapper.getClusters( name ,type,isLocal,description,apiUrl,accessIp,schStatus, identification,search,buildPage);
//        PageInfo<TbClusters> pageInfo = new PageInfo<>(list);
//        Page page = new Page();

        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo bindConfig(String body) {
        JSONObject jsonObject = JSONObject.parseObject(body);
        String clusterId = jsonObject.getString("clusterId");

        Integer configId = jsonObject.getInteger("configId");

//        ClusterConfig byClusterAndConfig = clusterConfigMapper.getByClusterAndConfig(clusterId, configId);
        ClusterConfig byClusterId = clusterConfigMapper.getByClusterId(clusterId);
        if (null!=byClusterId){
            byClusterId.setValid(true);
            byClusterId.setConfigId(configId);
            clusterConfigMapper.updateById(byClusterId);
            return ResultInfo.success();
        }else{
            ClusterConfig clusterConfig = new ClusterConfig();
            clusterConfig.setValid(true);
            clusterConfig.setConfigId(configId);
            clusterConfig.setClusterId(clusterId);
            clusterConfigMapper.insert(clusterConfig);
            return ResultInfo.success();
        }


    }

    @Override
    public ResultInfo getTurn(HttpServletRequest request, String id) {
        TurnServer byClusterId = turnServerMapper.getByClusterId(id);
        return ResultInfo.success(byClusterId);
    }

    @Override
    public ResultInfo getSignal(HttpServletRequest request, String id) {
        SignalServer byClusterId = signalServerMapper.getByClusterId(id);
        return ResultInfo.success(byClusterId);
    }

    @Override
    public ResultInfo getClusterList(HttpServletRequest request, Page buildPage) {
        QueryWrapper<TbClusters> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        List<TbClusters> tbClusters = tbClustersMapper.selectList(wrapper);
        return ResultInfo.success(tbClusters);
    }

    @Override
    public ResultInfo getClusterById(HttpServletRequest request, String id) {
        if(StringUtil.nullOrEmpty(id)){
            return  ResultInfo.fail("Id is null");
        }
        TbClusters tbClusters = tbClustersMapper.selectById(id);
        return  ResultInfo.success(tbClusters);
    }


}
