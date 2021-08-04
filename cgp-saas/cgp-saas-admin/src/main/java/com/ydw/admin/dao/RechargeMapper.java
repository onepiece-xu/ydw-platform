package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.Recharge;
import com.ydw.admin.model.vo.RechargeListVO;
import com.ydw.admin.model.vo.RechargeSummaryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 充值记录表 Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-09-24
 */
public interface RechargeMapper extends BaseMapper<Recharge> {

    Page<RechargeListVO> getRechargeList(Page buildPage,@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("search")String search);

    RechargeSummaryVO rechargeSummary(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("search") String search);

    Page<RechargeListVO> getRechargeListByEnterprise(Page buildPage, @Param("search") String search, @Param("list") List<String> channelIds);

    RechargeSummaryVO getRechargeCountByEnterprise(@Param("search") String search, @Param("list") List<String> channelIds);
}
