package com.ydw.recharge.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.recharge.model.db.WithdrawRecord;
import com.ydw.recharge.model.vo.WithdrawSummary;
import com.ydw.recharge.model.vo.WithdrawVO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
public interface WithdrawRecordMapper extends BaseMapper<WithdrawRecord> {

    IPage<WithdrawVO> getUserWithdrawRecord(Page buildPage, @Param("userId") String userId);

    WithdrawSummary getUserWithdrawSummary(String userId);
}
