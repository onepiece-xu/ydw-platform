package com.ydw.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.user.dao.SyncAppMapper;
import com.ydw.user.dao.TbUserAppsMapper;
import com.ydw.user.dao.TbUserAppsRelatedMapper;
import com.ydw.user.model.db.SyncApp;
import com.ydw.user.model.db.TbUserApps;
import com.ydw.user.model.db.TbUserAppsRelated;
import com.ydw.user.model.vo.App;
import com.ydw.user.model.vo.AppUseApproveVO;
import com.ydw.user.service.ITbUserAppsRelatedService;
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
 * @since 2020-08-06
 */
@Service
public class TbUserAppsRelatedServiceImpl extends ServiceImpl<TbUserAppsRelatedMapper, TbUserAppsRelated> implements ITbUserAppsRelatedService {

    @Autowired
    private TbUserAppsRelatedMapper tbUserAppsRelatedMapper;
    @Autowired
    private TbUserAppsMapper tbUserAppsMapper;

    @Autowired
    private SyncAppMapper syncAppMapper;

    @Override
    public ResultInfo getOwnerAppList(String body) {
        JSONObject jsonObject = JSON.parseObject(body);
        String enterpriseId = jsonObject.getString("enterpriseId");
        List<App> ownerAppList = tbUserAppsRelatedMapper.getOwnerAppList(enterpriseId);
        return ResultInfo.success(ownerAppList);
    }

    @Override
    public ResultInfo getSyncAppList(String body) {
        JSONObject jsonObject = JSON.parseObject(body);
        String enterpriseId = jsonObject.getString("enterpriseId");
        List<App> ownerAppList = syncAppMapper.getSyncAppList(enterpriseId);
        return ResultInfo.success(ownerAppList);
    }

    @Override
    public ResultInfo syncAppListDelete(String body) {
        JSONObject jsonObject = JSON.parseObject(body);
        String enterpriseId = jsonObject.getString("enterpriseId");
        QueryWrapper<SyncApp> wrapper = new QueryWrapper<>();
        wrapper.eq("identification",enterpriseId);
        syncAppMapper.delete(wrapper);
//        List<App> ownerAppList = syncAppMapper.getSyncAppList(enterpriseId);
        return ResultInfo.success();

    }

}
