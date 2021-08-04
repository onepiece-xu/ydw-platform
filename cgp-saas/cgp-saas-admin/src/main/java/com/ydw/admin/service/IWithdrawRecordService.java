package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.db.WithdrawRecord;
import com.ydw.admin.model.vo.ResultInfo;

import java.util.Date;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2020-12-21
 */
public interface IWithdrawRecordService extends IService<WithdrawRecord> {

    ResultInfo getUserWithdrawSummary(String userId);

    ResultInfo getUserWithdrawRecord(Page buildPage, String userId, Date beginDate, Date endDate);

    ResultInfo approvalWithdraw(WithdrawRecord withdrawRecord);

    ResultInfo getWithdrawRecord(Page buildPage, String search, Date beginDate, Date endDate,Integer status);
}
