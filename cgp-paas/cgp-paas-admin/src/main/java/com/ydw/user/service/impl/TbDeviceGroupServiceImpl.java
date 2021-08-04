package com.ydw.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
//import com.github.pagehelper.Page;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ydw.user.dao.TbDeviceGroupRelatedMapper;
import com.ydw.user.dao.TbDevicesMapper;
import com.ydw.user.model.db.TbDeviceGroup;
import com.ydw.user.dao.TbDeviceGroupMapper;
import com.ydw.user.model.db.TbDeviceGroupRelated;
import com.ydw.user.model.db.TbDevices;
import com.ydw.user.service.ITbDeviceGroupService;

import com.ydw.user.utils.ResultInfo;
import io.netty.handler.codec.json.JsonObjectDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
public class TbDeviceGroupServiceImpl extends ServiceImpl<TbDeviceGroupMapper, TbDeviceGroup> implements ITbDeviceGroupService {

    @Autowired
    private TbDeviceGroupMapper tbDeviceGroupMapper;

    @Autowired
    private TbDevicesMapper tbDevicesMapper;
    @Autowired
    private TbDeviceGroupRelatedMapper tbDeviceGroupRelatedMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public ResultInfo createDeviceGroup(HttpServletRequest request, TbDeviceGroup group) {

        group.setCreateTime(new Date());
        group.setValid(true);//有效
        group.setSchStatus(true);//启用
        tbDeviceGroupMapper.insert(group);
        logger.error(group.getName()+"设备组创建成功！");
        return ResultInfo.success();
    }

    @Override
    public ResultInfo updateDeviceGroup(HttpServletRequest request, TbDeviceGroup group) {
        group.setUpdateTime(new Date());
        tbDeviceGroupMapper.updateById(group);
        logger.error(group.getName()+"设备组编辑成功！");
        return  ResultInfo.success("Update success");
    }

    @Override
    public ResultInfo deleteDeviceGroup(HttpServletRequest request, List<Integer> ids) {
        String result="";
        TbDeviceGroup group = new TbDeviceGroup();

        for (Integer id : ids) {
            group.setId(id);
                //软删除
                group.setValid(false);
                group.setUpdateTime(new Date());
                tbDeviceGroupMapper.updateById(group);
                logger.info(group.getName()+"设备组删除成功！");
        }

        return  ResultInfo.success();
    }

    @Override
    public ResultInfo getDeviceGroupList(HttpServletRequest request, String name, String desc, Integer schStatus,String search,Page buildPage) {

        Page<TbDeviceGroup> list =tbDeviceGroupMapper.getDeviceGroupList(name,desc,schStatus,search,buildPage);

        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo addDevices(HttpServletRequest request, String body) {
        JSONObject object = JSONObject.parseObject(body);
        Integer groupId = object.getInteger("groupId");
        JSONArray ids = object.getJSONArray("ids");
        TbDeviceGroupRelated related = new TbDeviceGroupRelated();
        for (Object id:ids) {
            TbDeviceGroupRelated tr = tbDeviceGroupRelatedMapper.getByGroupIdDeviceId(groupId, id.toString());
            if(null==tr){
                related.setGroupId(groupId);
                related.setDeviceId(id.toString());
                related.setCreateTime(new Date());
                related.setValid(true);
                tbDeviceGroupRelatedMapper.insert(related);
            }else{
                related.setId(tr.getId());
                related.setValid(true);
                related.setModificationTime(new Date());
                tbDeviceGroupRelatedMapper.updateById(related);
            }
        }

        return ResultInfo.success();
    }

    @Override
    public ResultInfo removeDevices(HttpServletRequest request, String body) {
        JSONObject object = JSONObject.parseObject(body);
        Integer groupId = object.getInteger("groupId");
        JSONArray ids = object.getJSONArray("ids");
        TbDeviceGroupRelated related = new TbDeviceGroupRelated();
        for (Object id:ids) {
           TbDeviceGroupRelated tr=tbDeviceGroupRelatedMapper.getByGroupIdDeviceId(groupId,id.toString());
            related.setId(tr.getId());
            related.setValid(false);
            related.setModificationTime(new Date());
            tbDeviceGroupRelatedMapper.updateById(related);

        }

        return ResultInfo.success();
    }
}
