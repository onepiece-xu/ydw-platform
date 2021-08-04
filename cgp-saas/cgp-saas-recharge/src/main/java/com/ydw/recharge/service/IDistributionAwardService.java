package com.ydw.recharge.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.recharge.model.db.DistributionAward;
import com.ydw.recharge.model.vo.ResultInfo;

/**
 * <p>
 * 分销奖励表 服务类
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
public interface IDistributionAwardService extends IService<DistributionAward> {

    ResultInfo getUserDistributionAward(Page buildPage, String userId);
}
