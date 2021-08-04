package com.ydw.recharge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.recharge.model.db.DistributionAward;
import com.ydw.recharge.model.vo.DistributionAwardGroupDayVO;
import com.ydw.recharge.model.vo.DistributionAwardVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 分销奖励表 Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
public interface DistributionAwardMapper extends BaseMapper<DistributionAward> {


    IPage<DistributionAwardGroupDayVO> getUserDistributionAwardGroupByDay(Page buildPage, @Param("userId") String userId);

    DistributionAwardVO getUserDistributionAward(String userId);
}
