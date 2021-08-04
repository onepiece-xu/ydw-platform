package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.dao.EnterpriseMapper;
import com.ydw.admin.model.db.Enterprise;
import com.ydw.admin.service.IEnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-08-04
 */
@Service
public class EnterpriseServiceImpl extends ServiceImpl<EnterpriseMapper, Enterprise> implements IEnterpriseService {
    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public String getEnterpriseId() {
        List<Enterprise> enterprises = enterpriseMapper.selectList(new QueryWrapper<Enterprise>());
        return  enterprises.get(0).getIdentification();
    }
}
