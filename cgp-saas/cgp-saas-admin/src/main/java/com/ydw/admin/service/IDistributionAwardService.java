package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.db.DistributionAward;
import com.ydw.admin.model.vo.ResultInfo;

import java.util.Date;

/**
 * <p>
 * 分销奖励表 服务类
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
public interface IDistributionAwardService extends IService<DistributionAward> {

    ResultInfo getUserDistributionAwardSummary(String userId);

    ResultInfo getUserDistributionAward(Page buildPage, String userId, Date beginDate, Date endDate, String search);
}
