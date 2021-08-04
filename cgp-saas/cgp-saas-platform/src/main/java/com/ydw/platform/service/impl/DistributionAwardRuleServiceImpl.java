package com.ydw.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.dao.DistributionAwardRuleMapper;
import com.ydw.platform.model.db.DistributionAwardRule;
import com.ydw.platform.service.IDistributionAwardRuleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
@Service
public class DistributionAwardRuleServiceImpl extends ServiceImpl<DistributionAwardRuleMapper, DistributionAwardRule> implements IDistributionAwardRuleService {

    @Override
    public DistributionAwardRule getPullNewRule() {
        QueryWrapper<DistributionAwardRule> qw = new QueryWrapper<>();
        qw.eq("event",1);
        qw.eq("valid",true);
        return getOne(qw);
    }

    @Override
    public DistributionAwardRule getRegisterAwardRule() {
        QueryWrapper<DistributionAwardRule> qw = new QueryWrapper<>();
        qw.eq("event",3);
        qw.eq("valid",true);
        return getOne(qw);
    }
}
