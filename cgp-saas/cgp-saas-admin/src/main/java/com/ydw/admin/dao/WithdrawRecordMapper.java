package com.ydw.admin.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.WithdrawRecord;
import com.ydw.admin.model.vo.WithdrawSummaryVO;
import com.ydw.admin.model.vo.WithdrawVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
public interface WithdrawRecordMapper extends BaseMapper<WithdrawRecord> {

    WithdrawSummaryVO getUserWithdrawSummary(@Param("userId") String userId);

    IPage<WithdrawVO> getWithdrawRecord(Page buildPage,
                                        @Param("search")String search,
                                        @Param("beginDate")Date beginDate,
                                        @Param("endDate")Date endDate,
                                        @Param("status")Integer status);

}
