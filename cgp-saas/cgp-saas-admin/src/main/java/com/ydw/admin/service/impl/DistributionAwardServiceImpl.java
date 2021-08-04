package com.ydw.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.dao.DistributionAwardMapper;
import com.ydw.admin.model.db.DistributionAward;
import com.ydw.admin.model.db.UserInfo;
import com.ydw.admin.model.vo.DistributionAwardSummary;
import com.ydw.admin.model.vo.DistributionAwardVO;
import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IDistributionAwardService;
import com.ydw.admin.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 分销奖励表 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
@Service
public class DistributionAwardServiceImpl extends ServiceImpl<DistributionAwardMapper, DistributionAward> implements IDistributionAwardService {

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private DistributionAwardMapper distributionAwardMapper;

    @Override
    public ResultInfo getUserDistributionAwardSummary(String userId) {
        UserInfo userInfo = userInfoService.getById(userId);
        DistributionAwardSummary summary = new DistributionAwardSummary();
        summary.setBalance(userInfo.getBalance());
        summary.setProfit(userInfo.getProfit());
        summary.setUserId(userId);
        return ResultInfo.success(summary);
    }

    @Override
    public ResultInfo getUserDistributionAward(Page buildPage, String userId, Date beginDate, Date endDate, String search) {
        IPage<DistributionAwardVO> page = distributionAwardMapper.getUserDistributionAward(buildPage, userId, beginDate, endDate, search);
        return ResultInfo.success(page);
    }
}
