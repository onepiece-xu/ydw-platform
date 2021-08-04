package com.ydw.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.DailyReport;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.admin.model.vo.ResultInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xulh
 * @since 2021-03-02
 */
public interface IDailyReportService extends IService<DailyReport> {

    ResultInfo generateDailyReport();

    ResultInfo getNewUserPayment(String startTime, String endTime);

    ResultInfo getTotalPayment(String startTime, String endTime,Page buildPage);

    ResultInfo getRegisteredUserPayment(String startTime, String endTime);

    ResultInfo getNewUserPaymentRate(String startTime, String endTime);

    ResultInfo getUserTotalPaymentRate(String startTime, String endTime,Page buildPage);

    ResultInfo getActivityRate(String startTime, String endTime);

    ResultInfo getRetainedRate(String day);

    ResultInfo generateRetainedReport();

    ResultInfo retainedRateInfo(String day);

    ResultInfo getNextDayRetainedRate(String startTime, String endTime, Page buildPage);

    ResultInfo getThreeDayRetainedRate(String startTime, String endTime, Page buildPage);

    ResultInfo getSevenDayRetainedRate(String startTime, String endTime, Page buildPage);

    ResultInfo getFifteenDayRetainedRate(String startTime, String endTime, Page buildPage);

    ResultInfo getThirtyDayRetainedRate(String startTime, String endTime, Page buildPage);

    ResultInfo getDailyNewRegisterCount(String startTime, String endTime, Page buildPage);

    ResultInfo getDailyReport(String startTime, String endTime, Page buildPage);

    ResultInfo getRetainedInfo(String startTime, String endTime, Page buildPage);

    ResultInfo getPeriodActivity(String startTime, String endTime, Page buildPage);

    ResultInfo generatePeriodActivity();

    ResultInfo getConnectionsAndQueues(String startTime, String endTime, Page buildPage);

    ResultInfo generateConnectionsAndQueues();

    ResultInfo userClick(HttpServletRequest request,HashMap<String, String> map);

    ResultInfo getUserClick(String startTime, String endTime, Page buildPage);

    ResultInfo generateUserClick();
}
