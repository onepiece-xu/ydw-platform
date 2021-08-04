package com.ydw.platform.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.platform.model.db.DistributionAward;
import com.ydw.platform.model.vo.ResultInfo;

/**
 * <p>
 * 分销奖励表 服务类
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
public interface IDistributionAwardService extends IService<DistributionAward> {

    ResultInfo pullNewAward(String recommender, String inferior);

    ResultInfo registerAward(String userId);
}
