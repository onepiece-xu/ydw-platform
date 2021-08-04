package com.ydw.recharge.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.recharge.dao.DistributionAwardMapper;
import com.ydw.recharge.model.db.DistributionAward;
import com.ydw.recharge.model.vo.DistributionAwardGroupDayVO;
import com.ydw.recharge.model.vo.DistributionAwardVO;
import com.ydw.recharge.model.vo.ResultInfo;
import com.ydw.recharge.service.IDistributionAwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private DistributionAwardMapper distributionAwardMapper;

    @Override
    public ResultInfo getUserDistributionAward(Page buildPage, String userId) {
        DistributionAwardVO vo = distributionAwardMapper.getUserDistributionAward(userId);
        IPage<DistributionAwardGroupDayVO> page = distributionAwardMapper.getUserDistributionAwardGroupByDay(buildPage, userId);
        vo.setData(page);
        return ResultInfo.success(vo);
    }
}
