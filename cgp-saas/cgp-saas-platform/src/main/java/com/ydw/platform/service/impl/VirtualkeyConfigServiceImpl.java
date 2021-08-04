package com.ydw.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.dao.VirtualkeyConfigMapper;
import com.ydw.platform.model.db.App;
import com.ydw.platform.model.db.VirtualkeyConfig;
import com.ydw.platform.model.db.VirtualkeyRelation;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.model.vo.VirtualkeyConfigVO;
import com.ydw.platform.service.IAppService;
import com.ydw.platform.service.IVirtualkeyConfigService;
import com.ydw.platform.service.IVirtualkeyRelationService;
import com.ydw.platform.util.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-01-13
 */
@Service
public class VirtualkeyConfigServiceImpl extends ServiceImpl<VirtualkeyConfigMapper, VirtualkeyConfig> implements IVirtualkeyConfigService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAppService appService;

    @Autowired
    private IVirtualkeyRelationService virtualkeyRelationService;

    @Override
    public ResultInfo uploadVirtualkeyConfig(VirtualkeyConfigVO vo) {
        String appId = vo.getAppId();
        if (StringUtils.isBlank(vo.getAppId())){
            return ResultInfo.fail("appid为空！");
        }

        QueryWrapper<VirtualkeyConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(w -> w.eq("user_id", vo.getUserId())
                .eq("name", vo.getName())
                .eq("app_id", vo.getAppId()));
        QueryWrapper<VirtualkeyRelation> qw = new QueryWrapper<>();
        qw.eq("app_id", appId);
        qw.eq("user_id", "admin");
        List<VirtualkeyRelation> list = virtualkeyRelationService.list(qw);
        if (!list.isEmpty()){
            List<String> configIds = list.stream().map(VirtualkeyRelation::getConfigId).distinct().collect(Collectors.toList());
            queryWrapper.or(w-> w.in("id", configIds).eq("name", vo.getName()));
        }
        VirtualkeyConfig one = getOne(queryWrapper);
        if (one == null){
            one = new VirtualkeyConfig();
            one.setContent(vo.getContent());
            one.setAppId(appId);
            one.setUserId(vo.getUserId());
            one.setCreateTime(new Date());
            one.setCreateType(vo.getCreateType());
            one.setId(SequenceGenerator.sequence());
            one.setKeyType(vo.getKeyType());
            one.setName(vo.getName());
            one.setRemark(vo.getRemark());
            App app = appService.getById(appId);
            one.setAppType(app == null ? null : app.getType());
            super.save(one);
        }else{
            if (one.getCreateType() == 0){
                return ResultInfo.fail("请勿与系统配置重名！");
            }
            one.setRemark(vo.getRemark());
            one.setContent(vo.getContent());
            super.updateById(one);
        }
        return ResultInfo.success("新增成功！", one.getId());
    }

    /**
     *
     * @param userId,appId,name,content,configId,
     * @return
     */
    @Override
    public ResultInfo updateVirtualkeyConfig(VirtualkeyConfigVO vo) {
        VirtualkeyConfig config = super.getById(vo.getId());
        if (config.getName().equals(vo.getName())){
            if (config.getCreateType() == 0){
                return ResultInfo.fail("请勿与系统配置重名！");
            }
            config.setContent(vo.getContent());
            super.updateById(config);
            return ResultInfo.success("修改成功！", config.getId());
        }else{
            vo.setKeyType(config.getKeyType());
            vo.setCreateType(1);
            return uploadVirtualkeyConfig(vo);
        }
    }

    @Override
    public ResultInfo delVirtualkeyConfig(String configId, String userId) {
        UpdateWrapper<VirtualkeyConfig> uw = new UpdateWrapper<>();
        uw.eq("id", configId);
        uw.eq("user_id", userId);
        boolean remove = super.remove(uw);
        if (remove){
            UpdateWrapper<VirtualkeyRelation> uw1 = new UpdateWrapper<>();
            uw1.eq("config_id",configId);
            virtualkeyRelationService.remove(uw1);
            return ResultInfo.success();
        }else{
            return ResultInfo.fail("此配置当前不能被您删除！");
        }
    }

    @Override
    public ResultInfo getVirtualkeyConfigs(String userId, String appId, int keyType, Page page) {
        QueryWrapper<VirtualkeyRelation> qw = new QueryWrapper<>();
        qw.eq("app_id", appId);
        qw.and(w -> w.eq("user_id", userId).or().eq("user_id","admin"));
        List<VirtualkeyRelation> list = virtualkeyRelationService.list(qw);
        QueryWrapper<VirtualkeyConfig> q = new QueryWrapper<>();
        q.eq("key_type",keyType);
        if (list.isEmpty()){
            q.eq("user_id", userId).eq("app_id", appId);
        }else{
            q.and(w -> w.and(w1 -> w1.eq("user_id", userId).eq("app_id", appId))
                    .or().in("id", list.stream().map(VirtualkeyRelation::getConfigId).collect(Collectors.toList())));
        }
        page = super.page(page, q);
        return ResultInfo.success(page);
    }

}
