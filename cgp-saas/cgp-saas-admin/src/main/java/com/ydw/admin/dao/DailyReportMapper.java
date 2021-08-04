package com.ydw.admin.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.model.db.DailyReport;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydw.admin.model.vo.DailyReportVO;
import com.ydw.admin.model.vo.NewRegisterCountVO;
import com.ydw.admin.model.vo.RetainedRateVO;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xulh
 * @since 2021-03-02
 */
public interface DailyReportMapper extends BaseMapper<DailyReport> {
    /**
     * 当日新增注册用户
     * @param startTime
     * @param endTime
     * @return
     */
    Integer getNewRegisterCount(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 总注册用户数
     * @param startTime
     * @param endTime
     * @return
     */
    Integer getTotalUserCount(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 当日用户总付费额
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal getTotalPayment(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     *当日总付费用户数
     * @param startTime
     * @param endTime
     * @return
     */
    Integer getUserPaymentCount(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     *当日新增用户付费额
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal getNewUserPayment(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     *当日新增付费用户数
     * @param startTime
     * @param endTime
     * @return
     */
    Integer getNewPaymentCount(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     *当日总登录用户数
     * @param startTime
     * @param endTime
     * @return
     */
    Integer getTotalLogin(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     *当日连接用户总数
     * @param startTime
     * @param endTime
     * @return
     */
    Integer getTotalConnect(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * saas后台查询 新增用户付费额
     * @param startTime
     * @param endTime
     * @return
     */
    List<DailyReportVO> getNewRegisterPayment(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 用户总付费额
     * @param startTime
     * @param endTime
     * @return
     */
    Page<DailyReportVO> getUserTotalPayment(@Param("startTime")String startTime, @Param("endTime")String endTime,Page buildPage);

    /**
     * 已注册用户付费额
     * @param startTime
     * @param endTime
     * @return
     */
    List<DailyReportVO>  getRegisteredUserPayment(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 每日已注册用户付费额
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal getDayRegisteredUserPayment(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 新增用户付费率
     * @param startTime
     * @param endTime
     * @return
     */
    BigDecimal getDayNewUserPaymentRate(@Param("startTime")String startTime, @Param("endTime")String endTime);

    List<DailyReportVO> getNewUserPaymentRate(@Param("startTime")String startTime, @Param("endTime")String endTime);

    BigDecimal getDayUserTotalPaymentRate(@Param("startTime")String startTime, @Param("endTime")String endTime);

    Page<DailyReportVO> getUserTotalPaymentRate(@Param("startTime")String startTime, @Param("endTime")String endTime,Page buildPage);

    BigDecimal getDayActivityRate(@Param("startTime")String startTime, @Param("endTime")String endTime);

    List<DailyReportVO> getActivityRate(@Param("startTime")String startTime, @Param("endTime")String endTime);

    Integer getRetainedCount(@Param("startTime")String startTime, @Param("endTime")String endTime,@Param("startTimeYesterday")String startTimeYesterday, @Param("endTimeYesterday")String endTimeYesterday);

    Page<NewRegisterCountVO> getDailyNewRegisterCount(@Param("startTime")String startTime, @Param("endTime")String endTime, Page buildPage);

//    RetainedRateVO getRetainedInfo(@Param("day") String date);

    Integer generatePeriodActivity(@Param("startTime")String zero, @Param("endTime")String one);


    Page<RetainedRateVO> getRetainedInfo(@Param("startTime")String startTime, @Param("endTime")String endTime, Page buildPage);

    Integer getOnlineUserCount();

    Integer getPeriodClick(@Param("startTime")String zero, @Param("endTime")String one);
}
