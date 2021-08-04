package com.ydw.admin.controller;


import com.ydw.admin.model.vo.ResultInfo;
import com.ydw.admin.service.IDailyReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xulh
 * @since 2021-03-02
 */
@Controller
@RequestMapping("/dailyReport")
public class DailyReportController extends BaseController {
    @Autowired
    private IDailyReportService iDailyReportService;

    /**
     * 定时任务-生成报表
     */
    @GetMapping("/generateDailyReport")
    public ResultInfo generateDailyReport() {
        return iDailyReportService.generateDailyReport();
    }

    /**
     * 新增用户付费额查询
     */
    @GetMapping("/getNewUserPayment")
    public ResultInfo getNewUserPayment(@RequestParam String startTime, @RequestParam String endTime) {
        return iDailyReportService.getNewUserPayment(startTime, endTime);
    }

    /**
     * 用户总付费额查询
     */
    @GetMapping("/getTotalPayment")
    public ResultInfo getTotalPayment(@RequestParam (required = false) String startTime, @RequestParam (required = false) String endTime) {
        return iDailyReportService.getTotalPayment(startTime, endTime,buildPage());
    }

    /**
     * 老用户总付费额查询
     */
    @GetMapping("/getRegisteredUserPayment")
    public ResultInfo getRegisteredUserPayment(@RequestParam String startTime, @RequestParam String endTime) {
        return iDailyReportService.getRegisteredUserPayment(startTime, endTime);
    }

    /**
     * 新增用户付费率查询
     */
    @GetMapping("/getNewUserPaymentRate")
    public ResultInfo getNewUserPaymentRate(@RequestParam String startTime, @RequestParam String endTime) {
        return iDailyReportService.getNewUserPaymentRate(startTime, endTime);
    }

    /**
     * 用户总付费率  单日总付费用户数/单日总登录用户数
     */
    @GetMapping("/getUserTotalPaymentRate")
    public ResultInfo getUserTotalPaymentRate(@RequestParam (required = false) String startTime, @RequestParam (required = false)String endTime) {
        return iDailyReportService.getUserTotalPaymentRate(startTime, endTime,buildPage());
    }

    /**
     * 查询活跃度
     */
    @GetMapping("/getActivityRate")
    public ResultInfo getActivityRate(@RequestParam String startTime, @RequestParam String endTime) {
        return iDailyReportService.getActivityRate(startTime, endTime);
    }

    /**
     * 根据穿入日期生成留存率
     */
    @GetMapping("/getRetainedRate")
    public ResultInfo getRetainedRate(@RequestParam String day) {
        return iDailyReportService.getRetainedRate(day);
    }

    /**
     * 定时任务-生成留存率
     */
    @GetMapping("/generateRetainedReport")
    public ResultInfo generateRetainedReport() {
        return iDailyReportService.generateRetainedReport();
    }

    /**
     * 查询某日起的次留 三日留存 七日留存 十五日留存  三十日留存 存率详情
     */
    @GetMapping("/retainedRateInfo")
    public ResultInfo retainedRate(@RequestParam String day) {
        return iDailyReportService.retainedRateInfo(day);
    }

    /**
     *
     * 查询次日留存率
     */
    @GetMapping("/getNextDayRetainedRate")
    public ResultInfo getNextDayRetainedRate(@RequestParam (required = false)String startTime, @RequestParam (required = false) String endTime) {
        return iDailyReportService.getNextDayRetainedRate(startTime, endTime,buildPage());
    }
    /**
     *
     * 查询三日留存率
     */
    @GetMapping("/getThreeDayRetainedRate")
    public ResultInfo getThreeDayRetainedRate(@RequestParam (required = false) String startTime, @RequestParam (required = false)String endTime) {
        return iDailyReportService.getThreeDayRetainedRate(startTime, endTime,buildPage());
    }
    /**
     *
     * 查询七日留存率
     */
    @GetMapping("/getSevenDayRetainedRate")
    public ResultInfo getSevenDayRetainedRate(@RequestParam (required = false) String startTime, @RequestParam (required = false)String endTime) {
        return iDailyReportService.getSevenDayRetainedRate(startTime, endTime,buildPage());
    }
    /**
     *
     * 查询十五日留存率
     */
    @GetMapping("/getFifteenDayRetainedRate")
    public ResultInfo getFifteenDayRetainedRate(@RequestParam (required = false) String startTime,@RequestParam (required = false) String endTime) {
        return iDailyReportService.getFifteenDayRetainedRate(startTime, endTime,buildPage());
    }
    /**
     *
     * 查询三十日留存率
     */
    @GetMapping("/getThirtyDayRetainedRate")
    public ResultInfo getThirtyDayRetainedRate(@RequestParam (required = false)String startTime, @RequestParam (required = false) String endTime) {
        return iDailyReportService.getThirtyDayRetainedRate(startTime, endTime,buildPage());
    }
    /**
     * 新增用户数统计
     */
    @GetMapping("/getDailyNewRegisterCount")
    public ResultInfo getDailyNewRegisterCount(@RequestParam (required = false)String startTime, @RequestParam (required = false) String endTime) {
        return iDailyReportService.getDailyNewRegisterCount(startTime, endTime,buildPage());
    }
    /**
     * 查询各项
     */
    @GetMapping("/getDailyReport")
    public ResultInfo getDailyReport(@RequestParam (required = false)String startTime, @RequestParam (required = false) String endTime) {
        return iDailyReportService.getDailyReport(startTime, endTime,buildPage());
    }

    /**
     * 留存信息查询
     */
    @GetMapping("/getRetainedInfo")
    public ResultInfo getRetainedInfo(@RequestParam (required = false)String startTime, @RequestParam (required = false) String endTime) {
        return iDailyReportService.getRetainedInfo(startTime, endTime,buildPage());
    }

    /**
     * 查询活跃度
     */
    @GetMapping("/getPeriodActivity")
    public ResultInfo getPeriodActivity(@RequestParam (required = false)String startTime, @RequestParam (required = false) String endTime) {
        return iDailyReportService.getPeriodActivity(startTime, endTime,buildPage());
    }

    /**
     * 定时任务-生成活跃度报表
     */
    @GetMapping("/generatePeriodActivity")
    public ResultInfo generatePeriodActivity() {
        return iDailyReportService.generatePeriodActivity();
    }



    /**
     * 生成排队情况和在线用户数
     */
    @GetMapping("/generateConnectionsAndQueues")
    public ResultInfo generateConnectionsAndQueues() {
        return iDailyReportService.generateConnectionsAndQueues();
    }

    /**
     * 查询排队情况和在线用户数
     */
    @GetMapping("/getConnectionsAndQueues")
    public ResultInfo getConnectionsAndQueues(@RequestParam (required = false)String startTime, @RequestParam (required = false) String endTime) {
        return iDailyReportService.getConnectionsAndQueues(startTime, endTime,buildPage());
    }

    /**
     * APP统计用户点击次数
     */
    @PostMapping("/userClick")
    public ResultInfo userClick(HttpServletRequest request, @RequestBody HashMap<String, String> map ) {
        return iDailyReportService.userClick(request,map);
    }
    /**
     * 查询用户点击次数
     */
    @GetMapping("/getUserClick")
    public ResultInfo getUserClick( @RequestParam (required = false)String startTime, @RequestParam (required = false) String endTime ) {
        return iDailyReportService.getUserClick(startTime,endTime,buildPage());
    }

    /**
     * 生成用户点击日报
     */
    @GetMapping("/generateUserClick")
    public ResultInfo generateUserClick() {
        return iDailyReportService.generateUserClick();
    }
}

