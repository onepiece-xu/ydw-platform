package com.ydw.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.user.dao.AreaClusterMapper;
import com.ydw.user.model.db.Area;
import com.ydw.user.dao.AreaMapper;
import com.ydw.user.model.db.AreaCluster;
import com.ydw.user.model.vo.ClusterBindVO;
import com.ydw.user.service.IAreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.utils.ResultInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2020-09-04
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {



    @Autowired
    private AreaMapper areaMapper;

    @Autowired
    private AreaClusterMapper areaClusterMapper;

    @Override
    public ResultInfo getAreaList(Page buildPage) {
        QueryWrapper<Area> wrapper = new QueryWrapper<>();
        wrapper.eq("valid",1);
        Page page = areaMapper.selectPage(buildPage, wrapper);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo addArea(Area area) {
        area.setValid(true);
        areaMapper.insert(area);
        return ResultInfo.success("创建成功！");
    }

    @Override
    public ResultInfo updateArea(Area area) {
        areaMapper.updateById(area);
        return ResultInfo.success("编辑成功！");
    }

    @Override
    public ResultInfo deleteArea(List<Integer> ids) {
        String  msg="";
        QueryWrapper<AreaCluster> queryWrapper = new QueryWrapper<>();
        for (Integer id:ids) {
            Area byId = areaMapper.selectById(id);
            //判断区域下是否有集群
            queryWrapper.eq("area_id",id);
            List<AreaCluster> areaClusters = areaClusterMapper.selectList(queryWrapper);
            if(areaClusters.size()>0){
                msg+=byId.getName()+",";
            }
            Area area = areaMapper.selectById(id);
            area.setValid(false);
            areaMapper.updateById(area);
        }
        if(!msg.equals("")){
            msg="["+msg.substring(0,msg.length()-1)+"],删除失败，区域下存在集群！";
            return ResultInfo.fail();
        }else{
            return ResultInfo.success("");
        }

    }

    @Override
    public ResultInfo bind(String body) {
        JSONObject jsonObject = JSON.parseObject(body);
        JSONArray clusterIds = jsonObject.getJSONArray("clusterIds");
        String clusterId = jsonObject.getString("clusterId");
        Integer areaId = jsonObject.getInteger("areaId");
        AreaCluster areaCluster = new AreaCluster();
        for (Object id:clusterIds) {
            areaCluster.setClusterId(id.toString());
            areaCluster.setAreaId(areaId);
            areaCluster.setValid(true);
            areaClusterMapper.insert(areaCluster);
        }
        return ResultInfo.success("绑定成功！");
    }

    @Override
    public ResultInfo getUnbindList(Integer id,Page buildPage) {
        Page<ClusterBindVO> unbindList = areaMapper.getUnbindList(id,buildPage);
        return ResultInfo.success(unbindList);
    }

    @Override
    public ResultInfo getBindList(Integer id,Page buildPage) {
        Page<ClusterBindVO> bindList = areaMapper.getBindList(id,buildPage);
        return ResultInfo.success(bindList);
    }


}
