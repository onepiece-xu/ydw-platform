package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.DistributionAward;
import com.ydw.admin.model.vo.DistributionAwardVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * <p>
 * 分销奖励表 Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
public interface DistributionAwardMapper extends BaseMapper<DistributionAward> {

    IPage<DistributionAwardVO> getUserDistributionAward(Page buildPage, @Param("userId") String userId, @Param("beginDate") Date beginDate,
                                                        @Param("endDate") Date endDate, @Param("search") String search);

}
