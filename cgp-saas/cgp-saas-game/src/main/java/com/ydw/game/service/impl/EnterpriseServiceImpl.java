package com.ydw.game.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydw.game.model.db.Enterprise;
import com.ydw.game.dao.EnterpriseMapper;
import com.ydw.game.service.IEnterpriseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
