package com.ydw.user.service.impl;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.dao.*;
import com.ydw.user.model.db.*;
import com.ydw.user.model.vo.ClusterStatusVO;
import com.ydw.user.model.vo.CreateClusterVO;
import com.ydw.user.service.ITbClustersService;

import com.ydw.user.utils.ResultInfo;
import com.ydw.user.utils.SequenceGenerator;
import com.ydw.user.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
    private  SignalServerMapper signalServerMapper;
    @Autowired
    private ClusterOwnerMapper clusterOwnerMapper;


    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public ResultInfo createCluster(HttpServletRequest request, CreateClusterVO cluster) {
        String sequence = SequenceGenerator.sequence();
        String accessIp = cluster.getAccessIp();
        if (accessIp.contains(":")){
            String[] split = accessIp.split(":");
            cluster.setAccessIp(split[0]);
            cluster.setAccessPort(Integer.parseInt(split[1]));
        }
        cluster.setId(sequence);
        cluster.setCreateTime(new Date());
        cluster.setSchStatus(true);
        cluster.setValid(true);
        cluster.setDeviceNum(0);
        TbClusters tbClusters = new TbClusters();

        BeanUtils.copyProperties(cluster,tbClusters);
        tbClustersMapper.insert(tbClusters);
        List<String> userIds = cluster.getUserIds();
        for (String id:userIds) {
            ClusterOwner clusterOwner = new ClusterOwner();
            clusterOwner.setClusterId(sequence);
            clusterOwner.setOwnerId(id);
            clusterOwner.setValid(true);
            clusterOwnerMapper.insert(clusterOwner);
        }
        logger.info(cluster.getName()+"创建成功！");
        return ResultInfo.success();
    }

    @Override
    public ResultInfo updateCluster(HttpServletRequest request, CreateClusterVO cluster) {
        String clusterId = cluster.getId();
        QueryWrapper<ClusterOwner> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        wrapper.eq("cluster_id",clusterId);
        List<ClusterOwner> clusterOwners = clusterOwnerMapper.selectList(wrapper);
        for (ClusterOwner co: clusterOwners){
            co.setValid(false);
            clusterOwnerMapper.updateById(co);
        }

        cluster.setUpdateTime(new Date());
        List<String> userIdsNew = cluster.getUserIds();
        for (String  id: userIdsNew ) {
            ClusterOwner clusterOwner = new ClusterOwner();
            clusterOwner.setClusterId(clusterId);
            clusterOwner.setOwnerId(id);
            clusterOwner.setValid(true);
            clusterOwnerMapper.insert(clusterOwner);
        }


        TbClusters tbClusters = new TbClusters();
        BeanUtils.copyProperties(cluster,tbClusters);
        tbClustersMapper.updateById(tbClusters);
        logger.info(cluster.getName()+"编辑成功！");
        return ResultInfo.success("Update Success");
    }

    @Override
    public ResultInfo deleteClusters(HttpServletRequest request, List<String> ids) {
        String result="";
        String resultmsg="";
        String userMsg="";
        TbClusters clusters = new TbClusters();
        for (String id : ids) {
//            List<TbDeviceGroup> groupByClusterId = tbDeviceGroupMapper.getGroupByClusterId(id);
            List<TbDevices> devicesByClusterId = tbDevicesMapper.getDevicesByClusterId(id);
            TurnServer byClusterId = turnServerMapper.getByClusterId(id);
            List<String> ownerByCluster = clusterOwnerMapper.getOwnerByCluster(id);
            if(devicesByClusterId.size()>0){
                TbClusters tbClusters = tbClustersMapper.selectById(id);
                result=result+tbClusters.getName()+",";

            }
            if(null!=byClusterId){
                TbClusters tbClusters = tbClustersMapper.selectById(id);
                resultmsg=resultmsg+tbClusters.getName()+",";
            }
            if(null!=byClusterId){
                TbClusters tbClusters = tbClustersMapper.selectById(id);
                resultmsg=resultmsg+tbClusters.getName()+",";
            }
            if(ownerByCluster.size()>0){
                 userMsg="集群已绑定企业用户，请解绑后再操作！";
            }
            if(devicesByClusterId.size()==0&&null==byClusterId&&ownerByCluster.size()==0){
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
        if(userMsg!="") {
            logger.error(userMsg);
            return ResultInfo.fail(userMsg);
        }
        if(resultmsg!="") {
            resultmsg = resultmsg + "存在未删除的TurnServer,删除失败!";
            logger.error(resultmsg);
            return ResultInfo.fail(resultmsg);
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
        Page<CreateClusterVO> list =tbClustersMapper.getClusters( name ,type,isLocal,description,apiUrl,accessIp,schStatus, search,buildPage);
//        PageInfo<TbClusters> pageInfo = new PageInfo<>(list);
//        Page page = new Page();
        List<CreateClusterVO> records = list.getRecords();
        for (CreateClusterVO vo: records) {
            List<String> ownerByCluster = clusterOwnerMapper.getOwnerByCluster(vo.getId());
            vo.setUserIds(ownerByCluster);
        }

        list.setRecords(records);
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
    public ResultInfo getServiceClusters() {
        QueryWrapper<TbClusters> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        List<TbClusters> tbClusters = tbClustersMapper.selectList(wrapper);
        return ResultInfo.success(tbClusters);
    }

    @Override
    public ResultInfo getClusterStatus(HttpServletRequest request, String id) {
        ClusterStatusVO clusterStatus = tbClustersMapper.getClusterStatus(id);
        return ResultInfo.success(clusterStatus);
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
