package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.dao.VirtualkeyConfigMapper;
import com.ydw.admin.model.db.VirtualkeyConfig;
import com.ydw.admin.model.db.VirtualkeyRelation;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.model.vo.VirtualkeyConfigVO;
import com.ydw.admin.service.IVirtualkeyConfigService;
import com.ydw.admin.service.IVirtualkeyRelationService;
import com.ydw.admin.util.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
public class VirtualkeyConfigServiceImpl extends ServiceImpl<VirtualkeyConfigMapper, VirtualkeyConfig> implements IVirtualkeyConfigService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${ftp.address}")
    private String ftpAddress;

    @Value("${ftp.port}")
    private int ftpPort;

    @Value("${ftp.userName}")
    private String ftpUserName;

    @Value("${ftp.passWord}")
    private String ftpPassWord;

    @Autowired
    private IVirtualkeyRelationService virtualkeyRelationService;

    @Autowired
    private VirtualkeyConfigMapper virtualkeyConfigMapper;

    @Override
    public ResultInfo uploadVirtualkeyConfig(VirtualkeyConfigVO vo) {
        if (vo.getAppType() == null || StringUtils.isBlank(vo.getContent())
                || StringUtils.isBlank(vo.getName()) || vo.getKeyType() == null){
            return ResultInfo.fail("参数不正确");
        }
        VirtualkeyConfig v = new VirtualkeyConfig();
        v.setContent(vo.getContent());
        v.setRemark(vo.getRemark());
        v.setUserId(vo.getUserId());
        v.setCreateTime(new Date());
        v.setCreateType(vo.getCreateType());
        v.setAppType(vo.getAppType());
        v.setId(SequenceGenerator.sequence());
        v.setKeyType(vo.getKeyType());
        v.setName(vo.getName());
        super.save(v);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo updateVirtualkeyConfig(VirtualkeyConfigVO vo) {
        VirtualkeyConfig config = super.getById(vo.getId());
        if (StringUtils.isNotBlank(vo.getName())){
            config.setName(vo.getName());
        }
        if (StringUtils.isNotBlank(vo.getContent())){
            config.setContent(vo.getContent());
        }
        if (StringUtils.isNotBlank(vo.getRemark())){
            config.setRemark(vo.getRemark());
        }
        if (vo.getAppType() != null){
            config.setAppType(vo.getAppType());
        }
        updateById(config);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo delVirtualkeyConfig(List<String> configIds) {
        super.removeByIds(configIds);
        UpdateWrapper<VirtualkeyRelation> uw = new UpdateWrapper<>();
        uw.in("config_id",configIds);
        virtualkeyRelationService.remove(uw);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getVirtualkeyConfigs(IPage page, String search) {
        Page<VirtualkeyConfigVO> list = virtualkeyConfigMapper.getVirtualkeyConfigs(page,search);
        List<VirtualkeyConfigVO> records = list.getRecords();
        for (VirtualkeyConfigVO record : records) {
            record.buildApp();
            record.buildUser();
        }
        return ResultInfo.success(list);
    }

}
