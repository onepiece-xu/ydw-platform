package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.db.DistributionAwardRule;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
public interface IDistributionAwardRuleService extends IService<DistributionAwardRule> {

    DistributionAwardRule getPullNewRule();

    DistributionAwardRule getRegisterAwardRule();
}
