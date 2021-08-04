package com.ydw.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydw.platform.model.db.TemplateType;
import com.ydw.platform.dao.TemplateTypeMapper;
import com.ydw.platform.model.vo.ResultInfo;
import com.ydw.platform.service.ITemplateTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hea
 * @since 2020-11-04
 */
@Service
public class TemplateTypeServiceImpl extends ServiceImpl<TemplateTypeMapper, TemplateType> implements ITemplateTypeService {
    @Autowired
    private TemplateTypeMapper templateTypeMapper;
    @Override
    public ResultInfo getTemplateId(String term) {
        QueryWrapper<TemplateType> wrapper = new QueryWrapper<>();
        wrapper.eq("term",term);
        TemplateType templateType = templateTypeMapper.selectOne(wrapper);
        String templateId = templateType.getTemplateId();
        return ResultInfo.success("ok",templateId);
    }
}
