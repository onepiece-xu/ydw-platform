package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.dao.VirtualkeyRelationMapper;
import com.ydw.admin.model.db.App;
import com.ydw.admin.model.db.VirtualkeyRelation;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IVirtualkeyRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-01-13
 */
@Service
public class VirtualkeyRelationServiceImpl extends ServiceImpl<VirtualkeyRelationMapper, VirtualkeyRelation> implements IVirtualkeyRelationService {

    @Autowired
    private VirtualkeyRelationMapper virtualkeyRelationMapper;

    @Override
    @Transactional
    public ResultInfo bindApp(String configId, String[] appIds) {
        List<VirtualkeyRelation> list = new ArrayList<>();
        for (String appId : appIds){
            VirtualkeyRelation v = new VirtualkeyRelation();
            v.setAppId(appId);
            v.setConfigId(configId);
            v.setUserId("admin");
            v.setIsDefault(true);
            v.setCreateTime(new Date());
            list.add(v);
        }
        if (!list.isEmpty()){
            UpdateWrapper<VirtualkeyRelation> uw = new UpdateWrapper<>();
            uw.eq("user_id","admin");
            uw.in("app_id",appIds);
            uw.set("is_default",false);
            update(uw);
            saveBatch(list);
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo unbindApp(String configId, String[] appIds) {
        UpdateWrapper<VirtualkeyRelation> uw = new UpdateWrapper<>();
        uw.eq("config_id",configId);
        uw.in("app_id",appIds);
        remove(uw);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getBindedApp(Page buildPage, String id, String search) {
        Page<App> apps = virtualkeyRelationMapper.getBindedApp(buildPage, id, search);
        return ResultInfo.success(apps);
    }


    @Override
    public ResultInfo getUnBindApp(Page buildPage, String id, String search) {
        Page<App> apps = virtualkeyRelationMapper.getUnBindApp(buildPage, id, search);
        return ResultInfo.success(apps);
    }
}
