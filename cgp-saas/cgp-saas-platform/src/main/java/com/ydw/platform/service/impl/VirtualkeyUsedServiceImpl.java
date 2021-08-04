package com.ydw.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.dao.VirtualkeyUsedMapper;
import com.ydw.platform.model.db.VirtualkeyConfig;
import com.ydw.platform.model.db.VirtualkeyRelation;
import com.ydw.platform.model.db.VirtualkeyUsed;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.IVirtualkeyConfigService;
import com.ydw.platform.service.IVirtualkeyRelationService;
import com.ydw.platform.service.IVirtualkeyUsedService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-03-11
 */
@Service
public class VirtualkeyUsedServiceImpl extends ServiceImpl<VirtualkeyUsedMapper, VirtualkeyUsed> implements IVirtualkeyUsedService {

    @Autowired
    private IVirtualkeyConfigService virtualkeyConfigService;

    @Autowired
    private IVirtualkeyRelationService virtualkeyRelationService;

    @Override
    public ResultInfo applyVirtualkeyConfig(String configId, String appId, String userId) {
        QueryWrapper<VirtualkeyUsed> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_id", appId);
        queryWrapper.eq("user_id", userId);
        VirtualkeyUsed one = getOne(queryWrapper);
        if (one == null){
            VirtualkeyUsed v = new VirtualkeyUsed();
            v.setAppId(appId);
            v.setConfigId(configId);
            v.setUserId(userId);
            v.setCreateTime(new Date());
            save(v);
        }else{
            one.setConfigId(configId);
            updateById(one);
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getDefaultVirtualkeyConfig(String userId, String appId) {
        VirtualkeyConfig virtualkeyConfig = null;
        QueryWrapper<VirtualkeyUsed> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("app_id", appId);
        queryWrapper.eq("user_id", userId);
        VirtualkeyUsed one = getOne(queryWrapper);
        String configId = null;
        if (one == null){
            QueryWrapper<VirtualkeyRelation> qw = new QueryWrapper<>();
            qw.eq("app_id", appId).eq("user_id", "admin").eq("is_default",true);
            VirtualkeyRelation v = virtualkeyRelationService.getOne(qw);
            if (v != null){
                configId = v.getConfigId();
            }
        }else{
            configId = one.getConfigId();
        }
        if (StringUtils.isNotBlank(configId)){
            virtualkeyConfig = virtualkeyConfigService.getById(configId);
        }
        return ResultInfo.success(virtualkeyConfig);
    }
}
