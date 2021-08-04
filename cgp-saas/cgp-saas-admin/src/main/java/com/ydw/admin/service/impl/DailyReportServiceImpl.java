package com.ydw.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ydw.admin.dao.*;
import com.ydw.admin.model.db.*;
import com.ydw.admin.model.db.UserClicked;
import com.ydw.admin.model.vo.*;
import com.ydw.admin.service.IDailyReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.ydw.admin.service.IYdwAuthenticationService;
import com.ydw.admin.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-03-02
 */
@Service
public class DailyReportServiceImpl extends ServiceImpl<DailyReportMapper, DailyReport> implements IDailyReportService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DailyReportMapper dailyReportMapper;
    @Autowired
    private NextDayRetainedMapper nextDayRetainedMapper;
    @Autowired
    private ThreeDayRetainedMapper threeDayRetainedMapper;
    @Autowired
    private SevenDayRetainedMapper sevenDayRetainedMapper;
    @Autowired
    private FifteenDayRetainedMapper fifteenDayRetainedMapper;
    @Autowired
    private ThirtyDayRetainedMapper thirtyDayRetainedMapper;
    @Autowired
    private UserActivityMapper userActivityMapper;
    @Autowired
    private QueueInfoMapper queueInfoMapper;
    @Value("${url.paasApi}")
    private String paasApi;
    @Autowired
    private YdwPaasTokenService ydwPaasTokenService;
    @Autowired
    private UserClickMapper userClickMapper;
    @Autowired
    private UserClickedMapper userClickedMapper;
    @Autowired
    private IYdwAuthenticationService iYdwAuthenticationService;

    @Override
    public ResultInfo generateDailyReport() {

        //生存前一日的报表信息
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterdayDate = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String yesterday = formatter.format(yesterdayDate);

//        String  yesterday="2020-12-28";
        String startTime = DateToStringBeginOrEnd(yesterday, true);
        String endTime = DateToStringBeginOrEnd(yesterday, false);

        //生成报表
        DailyReport report = new DailyReport();
        report.setDateTime(yesterdayDate);
        //计算当日新增注册用户
        Integer newRegisterCount = dailyReportMapper.getNewRegisterCount(startTime, endTime);
        report.setNewRegisterCount(newRegisterCount);

        //当前总注册用户
        Integer totalUserCount = dailyReportMapper.getTotalUserCount(startTime, endTime);
        report.setTotalUserCount(totalUserCount);

        //当日用户总付费额
        BigDecimal totalPayment = dailyReportMapper.getTotalPayment(startTime, endTime);
        if (null != totalPayment) {
            report.setTotalPayment(totalPayment);
        } else {
            report.setTotalPayment(new BigDecimal(0.00));
        }

        //单日总付费用户数
        Integer userPaymentCount = dailyReportMapper.getUserPaymentCount(startTime, endTime);
        report.setUserPaymentCount(userPaymentCount);
        //当日新增用户付费额
        BigDecimal newUserPayment = dailyReportMapper.getNewUserPayment(startTime, endTime);
        if (null != newUserPayment) {
            report.setNewUserPayment(newUserPayment);
        } else {
            report.setNewUserPayment(new BigDecimal(0.00));
        }

        //单日新增付费用户数
        Integer newPaymentCount = dailyReportMapper.getNewPaymentCount(startTime, endTime);
        report.setNewPaymentCount(newPaymentCount);
        //单日总登录用户数
        Integer totalLogin = dailyReportMapper.getTotalLogin(startTime, endTime);
        // 单日连接用户总数
        report.setTotalLogin(totalLogin);
        Integer totalConnect = dailyReportMapper.getTotalConnect(startTime, endTime);
        report.setTotalConnect(totalConnect);
        try {
            Date time = formatter.parse(yesterday);

            QueryWrapper<DailyReport> wrapper = new QueryWrapper<>();
            wrapper.eq("date_time", time);
            DailyReport dailyReport = dailyReportMapper.selectOne(wrapper);
            if (null != dailyReport) {
                logger.error(yesterday + "该日-------生成报表已存在！");
                return ResultInfo.success("需要生成的报表已存在！");
            }
            dailyReportMapper.insert(report);
        } catch (Exception e) {
            logger.error(yesterday + "该日-------生成报表失败！");
            e.printStackTrace();
        }

        return ResultInfo.success();
    }

    @Override
    public ResultInfo getNewUserPayment(String startTime, String endTime) {

        if (startTime.equals(endTime)) {
            DailyReportVO dailyReportVO = new DailyReportVO();
            dailyReportVO.setDateTime(startTime);
            startTime = DateToStringBeginOrEnd(startTime, true);
            endTime = DateToStringBeginOrEnd(endTime, false);
            BigDecimal newUserPayment = dailyReportMapper.getNewUserPayment(startTime, endTime);


            if (null != newUserPayment) {
                dailyReportVO.setNewUserPayment(newUserPayment);
            } else {
                dailyReportVO.setNewUserPayment(new BigDecimal(0.00).setScale(2));
            }

            return ResultInfo.success(dailyReportVO);
        }
        List<DailyReportVO> newRegisterPayment = dailyReportMapper.getNewRegisterPayment(startTime, endTime);
        BigDecimal decimal = new BigDecimal(0.00);
        for (DailyReportVO vo : newRegisterPayment) {
            BigDecimal newUserPayment = vo.getNewUserPayment();
            decimal = decimal.add(newUserPayment);
        }

        return ResultInfo.success(decimal.toString(), newRegisterPayment);
    }

    @Override
    public ResultInfo getTotalPayment(String startTime, String endTime,Page buildPage) {
        if(!StringUtils.isNotEmpty(startTime)||!StringUtils.isNotEmpty(endTime)){
            return ResultInfo.success();
        }
        if (startTime.equals(endTime)) {
            DailyReportVO dailyReportVO = new DailyReportVO();

            dailyReportVO.setDateTime(startTime);
            startTime = DateToStringBeginOrEnd(startTime, true);
            endTime = DateToStringBeginOrEnd(endTime, false);
            BigDecimal totalPayment = dailyReportMapper.getTotalPayment(startTime, endTime);

            if (null != totalPayment) {
                dailyReportVO.setTotalPayment(totalPayment);
            } else {
                dailyReportVO.setTotalPayment(new BigDecimal(0.00).setScale(2));
            }
            Page<DailyReportVO> page = new Page<>();
            page.setTotal(1);
            page.setSize(1);
            page.setCurrent(1);
            List<DailyReportVO> list = new ArrayList<>();
            list.add(dailyReportVO);
            page.setRecords(list);
            return ResultInfo.success(page);
        }
        Page<DailyReportVO> userTotalPayment = dailyReportMapper.getUserTotalPayment(startTime, endTime,buildPage);
        List<DailyReportVO> records = userTotalPayment.getRecords();
        if(records.size()<=0){
            return ResultInfo.success();
        }
        BigDecimal decimal = new BigDecimal(0.00);
        for (DailyReportVO vo : records) {
            BigDecimal totalPayment = vo.getTotalPayment();
            decimal = decimal.add(totalPayment);
        }
        userTotalPayment.setRecords(records);
        return ResultInfo.success(userTotalPayment);
    }

    @Override
    public ResultInfo getRegisteredUserPayment(String startTime, String endTime) {

        if (startTime.equals(endTime)) {
            DailyReportVO dailyReportVO = new DailyReportVO();
            dailyReportVO.setDateTime(startTime);
            startTime = DateToStringBeginOrEnd(startTime, true);
            endTime = DateToStringBeginOrEnd(endTime, false);
            BigDecimal dayRegisteredUserPayment = dailyReportMapper.getDayRegisteredUserPayment(startTime, endTime);
            if (null != dayRegisteredUserPayment) {
                dailyReportVO.setRegisteredUserPayment(dayRegisteredUserPayment);
            } else {
                dailyReportVO.setRegisteredUserPayment(new BigDecimal(0.00).setScale(2));
            }

            return ResultInfo.success(dailyReportVO);
        }
        List<DailyReportVO> registeredUserPayment = dailyReportMapper.getRegisteredUserPayment(startTime, endTime);
        BigDecimal decimal = new BigDecimal(0.00);
        for (DailyReportVO vo : registeredUserPayment) {
            BigDecimal registeredUserPayment1 = vo.getRegisteredUserPayment();
            decimal = decimal.add(registeredUserPayment1);
        }

        return ResultInfo.success(decimal.toString(), registeredUserPayment);
    }

    @Override
    public ResultInfo getNewUserPaymentRate(String startTime, String endTime) {
        if (startTime.equals(endTime)) {
            DailyReportVO dailyReportVO = new DailyReportVO();
            dailyReportVO.setDateTime(startTime);
            startTime = DateToStringBeginOrEnd(startTime, true);
            endTime = DateToStringBeginOrEnd(endTime, false);
            BigDecimal dayNewUserPaymentRate = dailyReportMapper.getDayNewUserPaymentRate(startTime, endTime);

            if (null != dayNewUserPaymentRate) {
                dailyReportVO.setNewUserPaymentRate(dayNewUserPaymentRate);
            } else {
                dailyReportVO.setNewUserPaymentRate(new BigDecimal(0.00).setScale(4));
            }

            return ResultInfo.success(dailyReportVO);
        }
        List<DailyReportVO> newUserPaymentRate = dailyReportMapper.getNewUserPaymentRate(startTime, endTime);
        for (DailyReportVO vo : newUserPaymentRate) {
            BigDecimal rate = vo.getNewUserPaymentRate();
            if (null == rate) {
                vo.setNewUserPaymentRate(new BigDecimal(0.00).setScale(4));
            }
        }
        return ResultInfo.success(newUserPaymentRate);
    }


    @Override
    public ResultInfo getUserTotalPaymentRate(String startTime, String endTime,Page buildPage) {
        if(!StringUtils.isNotEmpty(startTime)||!StringUtils.isNotEmpty(endTime)){
            return ResultInfo.success();
        }
        if (startTime.equals(endTime)) {
            DailyReportVO dailyReportVO = new DailyReportVO();
            dailyReportVO.setDateTime(startTime);
            startTime = DateToStringBeginOrEnd(startTime, true);
            endTime = DateToStringBeginOrEnd(endTime, false);
            BigDecimal dayUserTotalPaymentRate = dailyReportMapper.getDayUserTotalPaymentRate(startTime, endTime);
//            if(dayUserTotalPaymentRate.getSize()<=0){
//                 dailyReportVO = new DailyReportVO();
//                dailyReportVO.setUserTotalPaymentRate(new BigDecimal(0.00).setScale(4));
//                return ResultInfo.success(dailyReportVO);
//            }
            if (null != dayUserTotalPaymentRate) {
                dailyReportVO.setUserTotalPaymentRate(dayUserTotalPaymentRate);
            } else {
                dailyReportVO.setUserTotalPaymentRate(new BigDecimal(0.00).setScale(4));
            }
            Page<DailyReportVO> data = new Page<>();
            List<DailyReportVO> vo = new ArrayList<>();
            vo.add(dailyReportVO);
            data.setRecords(vo);
            data.setTotal(1);
            return ResultInfo.success(data);
        }
        Page<DailyReportVO> userTotalPaymentRate = dailyReportMapper.getUserTotalPaymentRate(startTime, endTime,buildPage);
        List<DailyReportVO> records = userTotalPaymentRate.getRecords();
        for (DailyReportVO vo :records) {
            BigDecimal rate = vo.getUserTotalPaymentRate();
            if (null == rate) {
                vo.setNewUserPaymentRate(new BigDecimal(0.00).setScale(4));
            }

        }
        userTotalPaymentRate.setRecords(records);
        return ResultInfo.success(userTotalPaymentRate);
    }

    @Override
    public ResultInfo getActivityRate(String startTime, String endTime) {
        if (startTime.equals(endTime)) {
            DailyReportVO dailyReportVO = new DailyReportVO();
            dailyReportVO.setDateTime(startTime);
            startTime = DateToStringBeginOrEnd(startTime, true);
            endTime = DateToStringBeginOrEnd(endTime, false);
            BigDecimal dayActivityRate = dailyReportMapper.getDayActivityRate(startTime, endTime);
            if (null != dayActivityRate) {
                dailyReportVO.setActivityRate(dayActivityRate);
            } else {
                dailyReportVO.setActivityRate(new BigDecimal(0.00).setScale(4));
            }
            return ResultInfo.success(dailyReportVO);
        }
        List<DailyReportVO> activityRate = dailyReportMapper.getActivityRate(startTime, endTime);
        for (DailyReportVO vo : activityRate) {
            BigDecimal rate = vo.getActivityRate();
            if (null != rate) {
                vo.setActivityRate(rate);
            } else {
                vo.setActivityRate(new BigDecimal(0.00).setScale(4));
            }
        }
        return ResultInfo.success(activityRate);
    }

    @Override
    public ResultInfo getRetainedRate(String day) {

        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
            Date date= simpleDateFormat.parse(day);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date yesterday = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date thedaybeforeyesterdayDate = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String thedaybeforeyesterday = formatter.format(thedaybeforeyesterdayDate);
            String yesterdayformat = formatter.format(yesterday);
            //插入次日留存表
            inserRetained(thedaybeforeyesterday, yesterdayformat, 1);

            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Date threedaybeforeDate = calendar.getTime();
            String threedaybefore = formatter.format(threedaybeforeDate);

            //插入3日留存表
            inserRetained(threedaybefore, yesterdayformat, 2);

            calendar.add(Calendar.DAY_OF_MONTH, -4);
            Date sevendaybeforeDate = calendar.getTime();
            String sevendaybefore = formatter.format(sevendaybeforeDate);

            //插入7日留存表
            inserRetained(sevendaybefore, yesterdayformat, 3);

            calendar.add(Calendar.DAY_OF_MONTH, -8);
            Date fifteendaybeforeDate = calendar.getTime();
            String fifteendaybefore = formatter.format(fifteendaybeforeDate);
            //插入15日留存表
            inserRetained(fifteendaybefore, yesterdayformat, 4);

            calendar.add(Calendar.DAY_OF_MONTH, -15);
            Date thirtydaybeforeDate = calendar.getTime();
            String thirtydaybefore = formatter.format(thirtydaybeforeDate);

            //插入30日留存表
            inserRetained(thirtydaybefore, yesterdayformat, 5);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo generateRetainedReport() {
        //生存前一日的报表信息
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date thedaybeforeyesterdayDate = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String thedaybeforeyesterday = formatter.format(thedaybeforeyesterdayDate);
        String yesterdayformat = formatter.format(yesterday);
        //插入次日留存表
        inserRetained(thedaybeforeyesterday, yesterdayformat, 1);

        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date threedaybeforeDate = calendar.getTime();
        String threedaybefore = formatter.format(threedaybeforeDate);

        //插入3日留存表
        inserRetained(threedaybefore, yesterdayformat, 2);

        calendar.add(Calendar.DAY_OF_MONTH, -4);
        Date sevendaybeforeDate = calendar.getTime();
        String sevendaybefore = formatter.format(sevendaybeforeDate);

        //插入7日留存表
        inserRetained(sevendaybefore, yesterdayformat, 3);

        calendar.add(Calendar.DAY_OF_MONTH, -8);
        Date fifteendaybeforeDate = calendar.getTime();
        String fifteendaybefore = formatter.format(fifteendaybeforeDate);
        //插入15日留存表
        inserRetained(fifteendaybefore, yesterdayformat, 4);

        calendar.add(Calendar.DAY_OF_MONTH, -15);
        Date thirtydaybeforeDate = calendar.getTime();
        String thirtydaybefore = formatter.format(thirtydaybeforeDate);

        //插入30日留存表
        inserRetained(thirtydaybefore, yesterdayformat, 5);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo retainedRateInfo(String day) {

        Calendar calendar = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
            Date date= simpleDateFormat.parse(day);
            calendar = Calendar.getInstance();
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DAY_OF_MONTH, +2);
        Date thedaybeforeyesterdayDate = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String thedaybeforeyesterday = formatter.format(thedaybeforeyesterdayDate);
        RetainedRateInfoVO retainedRateInfoVO = new RetainedRateInfoVO();
        //插入次日留存表
        QueryWrapper<NextDayRetained> wrapper = new QueryWrapper<>();
        wrapper.eq("date_time",thedaybeforeyesterday);
        NextDayRetained nextDayRetained = nextDayRetainedMapper.selectOne(wrapper);
        if(null!=nextDayRetained){
            retainedRateInfoVO.setNextDaydateTime(nextDayRetained.getDateTime());
            retainedRateInfoVO.setNextDayLoginCount(nextDayRetained.getLoginCount());
            retainedRateInfoVO.setNextDayRegisterCount(nextDayRetained.getRegisterCount());
            retainedRateInfoVO.setNextDayRate(nextDayRetained.getRate());
        }
        //3日留存表
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        Date threedaybeforeDate = calendar.getTime();
        String threedaybefore = formatter.format(threedaybeforeDate);
        QueryWrapper<ThreeDayRetained> threeDayRetainedQueryWrapper = new QueryWrapper<>();
        threeDayRetainedQueryWrapper.eq("date_time",threedaybefore);
        ThreeDayRetained threeDayRetained = threeDayRetainedMapper.selectOne(threeDayRetainedQueryWrapper);
        if(null!=threeDayRetained){
            retainedRateInfoVO.setThreeDaydateTime(threeDayRetained.getDateTime());
            retainedRateInfoVO.setThreeDayLoginCount(threeDayRetained.getLoginCount());
            retainedRateInfoVO.setThreeDayRegisterCount(threeDayRetained.getRegisterCount());
            retainedRateInfoVO.setThreeDayRate(threeDayRetained.getRate());
        }



        calendar.add(Calendar.DAY_OF_MONTH, +4);
        Date sevendaybeforeDate = calendar.getTime();
        String sevendaybefore = formatter.format(sevendaybeforeDate);

        //7日留存表
        QueryWrapper<SevenDayRetained> sevenDayRetainedQueryWrapper = new QueryWrapper<>();
        sevenDayRetainedQueryWrapper.eq("date_time",sevendaybefore);
        SevenDayRetained sevenDayRetained = sevenDayRetainedMapper.selectOne(sevenDayRetainedQueryWrapper);
        if(null!=sevenDayRetained){
            retainedRateInfoVO.setSevenDaydateTime(sevenDayRetained.getDateTime());
            retainedRateInfoVO.setSevenDayLoginCount(sevenDayRetained.getLoginCount());
            retainedRateInfoVO.setSevenDayRegisterCount(sevenDayRetained.getRegisterCount());
            retainedRateInfoVO.setSevenDayRate(sevenDayRetained.getRate());
        }


        calendar.add(Calendar.DAY_OF_MONTH, +8);
        Date fifteendaybeforeDate = calendar.getTime();
        String fifteendaybefore = formatter.format(fifteendaybeforeDate);
        //15日留存表
        QueryWrapper<FifteenDayRetained> fifteenDayRetainedQueryWrapper = new QueryWrapper<>();
        fifteenDayRetainedQueryWrapper.eq("date_time",fifteendaybefore);
        FifteenDayRetained fifteenDayRetained = fifteenDayRetainedMapper.selectOne(fifteenDayRetainedQueryWrapper);
        if(null!=sevenDayRetained){
            retainedRateInfoVO.setFifteenDaydateTime(fifteenDayRetained.getDateTime());
            retainedRateInfoVO.setFifteenDayLoginCount(fifteenDayRetained.getLoginCount());
            retainedRateInfoVO.setFifteenDayRegisterCount(fifteenDayRetained.getRegisterCount());
            retainedRateInfoVO.setFifteenDayRate(fifteenDayRetained.getRate());
        }




        calendar.add(Calendar.DAY_OF_MONTH, +15);
        Date thirtydaybeforeDate = calendar.getTime();
        String thirtydaybefore = formatter.format(thirtydaybeforeDate);
        //30日留存
        QueryWrapper<ThirtyDayRetained> thirtyDayRetainedQueryWrapper = new QueryWrapper<>();
        thirtyDayRetainedQueryWrapper.eq("date_time",thirtydaybefore);
        ThirtyDayRetained thirtyDayRetained = thirtyDayRetainedMapper.selectOne(thirtyDayRetainedQueryWrapper);
        if(null!=sevenDayRetained){
            retainedRateInfoVO.setThiryDaydateTime(thirtyDayRetained.getDateTime());
            retainedRateInfoVO.setThreeDayLoginCount(thirtyDayRetained.getLoginCount());
            retainedRateInfoVO.setThirtyDayRegisterCount(thirtyDayRetained.getRegisterCount());
            retainedRateInfoVO.setThirtyDayRate(thirtyDayRetained.getRate());
        }
        return ResultInfo.success(retainedRateInfoVO);
    }

    @Override
    public ResultInfo getNextDayRetainedRate(String startTime, String endTime, Page buildPage) {
        if(!StringUtils.isNotEmpty(startTime)||!StringUtils.isNotEmpty(endTime)){
            return ResultInfo.success();
        }
        Page<NextDayRetained> nextDayRetainedRate = nextDayRetainedMapper.getNextDayRetainedRate(startTime, endTime, buildPage);

        return ResultInfo.success(nextDayRetainedRate);
    }

    @Override
    public ResultInfo getThreeDayRetainedRate(String startTime, String endTime, Page buildPage) {
        if(!StringUtils.isNotEmpty(startTime)||!StringUtils.isNotEmpty(endTime)){
            return ResultInfo.success();
        }
        Page<ThreeDayRetained> threeDayRetainedRate = threeDayRetainedMapper.getThreeDayRetainedRate(startTime, endTime,buildPage);
        return ResultInfo.success(threeDayRetainedRate);
    }

    @Override
    public ResultInfo getSevenDayRetainedRate(String startTime, String endTime, Page buildPage) {
        if(!StringUtils.isNotEmpty(startTime)||!StringUtils.isNotEmpty(endTime)){
            return ResultInfo.success();
        }
        Page<SevenDayRetained> sevenDayRetainedRate = sevenDayRetainedMapper.getSevenDayRetainedRate(startTime, endTime,buildPage);
        return ResultInfo.success(sevenDayRetainedRate);
    }

    @Override
    public ResultInfo getFifteenDayRetainedRate(String startTime, String endTime, Page buildPage) {
        if(!StringUtils.isNotEmpty(startTime)||!StringUtils.isNotEmpty(endTime)){
            return ResultInfo.success();
        }
        Page<FifteenDayRetained> fifteenDayRetainedRate = fifteenDayRetainedMapper.getFifteenDayRetainedRate(startTime, endTime,buildPage);
        return ResultInfo.success(fifteenDayRetainedRate);
    }

    @Override
    public ResultInfo getThirtyDayRetainedRate(String startTime, String endTime,Page buildPage) {
        if(!StringUtils.isNotEmpty(startTime)||!StringUtils.isNotEmpty(endTime)){
            return ResultInfo.success();
        }
        Page<ThirtyDayRetained> thirtyDayRetainedRate = thirtyDayRetainedMapper.getThirtyDayRetainedRate(startTime, endTime,buildPage);
        return ResultInfo.success(thirtyDayRetainedRate);
    }

    @Override
    public ResultInfo getDailyNewRegisterCount(String startTime, String endTime, Page buildPage) {
        if(!StringUtils.isNotEmpty(startTime)||!StringUtils.isNotEmpty(endTime)){
            return ResultInfo.success();
        }
        Page<NewRegisterCountVO> dailyNewRegisterCount = dailyReportMapper.getDailyNewRegisterCount(startTime, endTime,buildPage);
        return ResultInfo.success(dailyNewRegisterCount);
    }

    @Override
    public ResultInfo getDailyReport(String startTime, String endTime, Page buildPage) {
        QueryWrapper<DailyReport> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("date_time");
        if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime)){
            wrapper.ge("date_time",startTime);
            wrapper.le("date_time",endTime);
        }
        Page page = dailyReportMapper.selectPage(buildPage, wrapper);

        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo getRetainedInfo(String startTime, String endTime, Page buildPage) {
        Page<RetainedRateVO> retainedInfo = dailyReportMapper.getRetainedInfo(startTime, endTime, buildPage);
        return ResultInfo.success(retainedInfo);
    }

    @Override
    public ResultInfo getPeriodActivity(String startTime, String endTime, Page buildPage) {
        QueryWrapper<UserActivity> wrapper = new QueryWrapper<>();
        wrapper.ge("date_time",startTime);
        wrapper.le("date_time",endTime);
        wrapper.orderByDesc("date_time");
        Page page = userActivityMapper.selectPage(buildPage, wrapper);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo generatePeriodActivity() {
        //生存前一日的报表信息
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.add(Calendar.HOUR_OF_DAY, -24);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        QueryWrapper<UserActivity> wrapper = new QueryWrapper<>();
        String format = fm.format(calendar.getTime());
        wrapper.eq("date_time",format);
        UserActivity res = userActivityMapper.selectOne(wrapper);
        if(null!=res){
            return ResultInfo.success("活跃度记录已存在！");
        }
        UserActivity userActivity = new UserActivity();
        userActivity.setDateTime(calendar.getTime());
        String zero = formatter.format(calendar.getTime());
        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String one = formatter.format(calendar.getTime());
        Integer integer0 = dailyReportMapper.generatePeriodActivity(zero, one);
        userActivity.setZero(integer0);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String two = formatter.format(calendar.getTime());
        Integer integer1 = dailyReportMapper.generatePeriodActivity(one,two);
        userActivity.setOne(integer1);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String three = formatter.format(calendar.getTime());
        Integer integer2 = dailyReportMapper.generatePeriodActivity(two,three);
        userActivity.setTwo(integer2);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String four = formatter.format(calendar.getTime());
        Integer integer3 = dailyReportMapper.generatePeriodActivity(three,four);
        userActivity.setThree(integer3);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String five = formatter.format(calendar.getTime());
        Integer integer4 = dailyReportMapper.generatePeriodActivity(four,five);
        userActivity.setFour(integer4);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String six = formatter.format(calendar.getTime());
        Integer integer5 = dailyReportMapper.generatePeriodActivity(five,six);
        userActivity.setFive(integer5);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String seven = formatter.format(calendar.getTime());
        Integer integer6 = dailyReportMapper.generatePeriodActivity(six,seven);
        userActivity.setSix(integer6);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String eight = formatter.format(calendar.getTime());
        Integer integer7= dailyReportMapper.generatePeriodActivity(seven,eight);
        userActivity.setSeven(integer7);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String nine = formatter.format(calendar.getTime());
        Integer integer8 = dailyReportMapper.generatePeriodActivity(eight,nine);
        userActivity.setEight(integer8);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String ten = formatter.format(calendar.getTime());
        Integer integer9 = dailyReportMapper.generatePeriodActivity(nine,ten);
        userActivity.setNine(integer9);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String eleven = formatter.format(calendar.getTime());
        Integer integer10 = dailyReportMapper.generatePeriodActivity(ten,eleven);
        userActivity.setTen(integer10);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String twelve = formatter.format(calendar.getTime());
        Integer integer11 = dailyReportMapper.generatePeriodActivity(eleven,twelve);
        userActivity.setEleven(integer11);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String thirteen = formatter.format(calendar.getTime());
        Integer integer12 = dailyReportMapper.generatePeriodActivity(twelve,thirteen);
        userActivity.setTwelve(integer12);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String fourteen = formatter.format(calendar.getTime());
        Integer integer13 = dailyReportMapper.generatePeriodActivity(thirteen,fourteen);
        userActivity.setThirteen(integer13);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String fifteen = formatter.format(calendar.getTime());
        Integer integer14 = dailyReportMapper.generatePeriodActivity(fourteen,fifteen);
        userActivity.setFourteen(integer14);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String sixteen = formatter.format(calendar.getTime());
        Integer integer15 = dailyReportMapper.generatePeriodActivity(fifteen,sixteen);
        userActivity.setFifteen(integer15);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String seventeen = formatter.format(calendar.getTime());
        Integer integer16 = dailyReportMapper.generatePeriodActivity(sixteen,seventeen);
        userActivity.setSixteen(integer16);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String eighteen = formatter.format(calendar.getTime());
        Integer integer17 = dailyReportMapper.generatePeriodActivity(seventeen,eighteen);
        userActivity.setSeventeen(integer17);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String nineteen = formatter.format(calendar.getTime());
        Integer integer18 = dailyReportMapper.generatePeriodActivity(eighteen,nineteen);
        userActivity.setEighteen(integer18);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String twenty = formatter.format(calendar.getTime());
        Integer integer19 = dailyReportMapper.generatePeriodActivity(nineteen,twenty);
        userActivity.setNineteen(integer19);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String twentyOne = formatter.format(calendar.getTime());
        Integer integer20 = dailyReportMapper.generatePeriodActivity(twenty,twentyOne);
        userActivity.setTwenty(integer20);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String twentyTwo = formatter.format(calendar.getTime());
        Integer integer21 = dailyReportMapper.generatePeriodActivity(twentyOne,twentyTwo);
        userActivity.setTwentyOne(integer21);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String twentyThree = formatter.format(calendar.getTime());
        Integer integer22 = dailyReportMapper.generatePeriodActivity(twentyTwo,twentyThree);
        userActivity.setTwentyTwo(integer22);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String twentyFour = formatter.format(calendar.getTime());
        Integer integer23 = dailyReportMapper.generatePeriodActivity(twentyThree,twentyFour);
        userActivity.setTwentyThree(integer23);
        userActivityMapper.insert(userActivity);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getConnectionsAndQueues(String startTime, String endTime, Page buildPage) {
        if(!StringUtils.isNotEmpty(startTime)||!StringUtils.isNotEmpty(endTime)){
            return ResultInfo.success();
        }
        startTime = DateToStringBeginOrEnd(startTime, true);
        endTime = DateToStringBeginOrEnd(endTime, false);
        QueryWrapper<QueueInfo> wrapper = new QueryWrapper<>();
        wrapper.gt("date_time",startTime);
        wrapper.le("date_time",endTime);
        wrapper.orderByDesc("date_time");
        Page page = queueInfoMapper.selectPage(buildPage, wrapper);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo generateConnectionsAndQueues() {
        Date now = new Date();
        Integer onlineUserCount = dailyReportMapper.getOnlineUserCount();
        QueueInfo queueInfo = new QueueInfo();
        queueInfo.setConnectNum(onlineUserCount);
        queueInfo.setDateTime(now);
        //TODO 添加排队人数
        try {
            String url = paasApi + "/cgp-paas-schedule/queue/getClustersQueueNum";
            Map<String, String> headers = new HashMap<>();
            String enterprisePaasToken = ydwPaasTokenService.getPaasToken();
            headers.put("Authorization", enterprisePaasToken);
            String doGet = HttpUtil.doGet(url, headers,null);
//        JSONObject jsonObject = JSON.parseObject(doGet);
            ResultInfo info = JSON.parseObject(JSON.parse(doGet).toString(), ResultInfo.class);

            String jsonString = JSON.toJSONString(info.getData());
            List<AppQueueVO> data = JSONObject.parseArray(jsonString, AppQueueVO.class);
            for(AppQueueVO vo:data){
                if (vo.getClusterId().equals("ALL")){
                    queueInfo.setQueueNum(vo.getQueueNum().intValue());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        queueInfoMapper.insert(queueInfo);
        return ResultInfo.success(queueInfo);
    }

    @Override
    public ResultInfo userClick(HttpServletRequest request, HashMap<String, String> map) {
        String appId = map.get("appId");
        String userId = null;
        try {
            String authorization = request.getHeader("Authorization");
            String userInfoByToken = iYdwAuthenticationService.getUserInfoByToken(authorization);
            ResultInfo res = JSON.parseObject(userInfoByToken, ResultInfo.class);
            String jsonString = JSON.toJSONString(res.getData());
            UserInfo userInfo = JSONObject.parseObject(jsonString, UserInfo.class);
            userId = userInfo.getId();
        } catch (Exception e) {
            logger.error("userId 获取失败-----------");
            e.printStackTrace();
        }
        Date date = new Date();
        UserClick userClick = new UserClick();
        userClick.setCreateTime(date);
        userClick.setAppId(appId);
        userClick.setUserId(userId);
        userClickMapper.insert(userClick);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getUserClick(String startTime, String endTime, Page buildPage) {
        if(!StringUtils.isNotEmpty(startTime)||!StringUtils.isNotEmpty(endTime)){
            return ResultInfo.success();
        }
        startTime = DateToStringBeginOrEnd(startTime, true);
        endTime = DateToStringBeginOrEnd(endTime, false);
        QueryWrapper<UserClicked> wrapper = new QueryWrapper<>();
        wrapper.ge("date_time",startTime);
        wrapper.le("date_time",endTime);
        wrapper.orderByDesc("date_time");
        Page page = userClickedMapper.selectPage(buildPage, wrapper);
        return ResultInfo.success(page);

    }

    @Override
    public ResultInfo generateUserClick() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.add(Calendar.HOUR_OF_DAY, -24);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
        QueryWrapper<UserClicked> wrapper = new QueryWrapper<>();

        String format = fm.format(calendar.getTime());
        wrapper.eq("date_time",format);
        UserClicked res = userClickedMapper.selectOne(wrapper);
        if(null!=res){
            return ResultInfo.success("记录已存在！");
        }
        UserClicked userClicked = new UserClicked();
        userClicked.setDateTime(calendar.getTime());
        String zero = formatter.format(calendar.getTime());
        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String one = formatter.format(calendar.getTime());
        Integer integer0 = dailyReportMapper.getPeriodClick(zero, one);
        userClicked.setZero(integer0);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String two = formatter.format(calendar.getTime());
        Integer integer1 = dailyReportMapper.getPeriodClick(one,two);
        userClicked.setOne(integer1);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String three = formatter.format(calendar.getTime());
        Integer integer2 = dailyReportMapper.getPeriodClick(two,three);
        userClicked.setTwo(integer2);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String four = formatter.format(calendar.getTime());
        Integer integer3 = dailyReportMapper.getPeriodClick(three,four);
        userClicked.setThree(integer3);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String five = formatter.format(calendar.getTime());
        Integer integer4 = dailyReportMapper.getPeriodClick(four,five);
        userClicked.setFour(integer4);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String six = formatter.format(calendar.getTime());
        Integer integer5 = dailyReportMapper.getPeriodClick(five,six);
        userClicked.setFive(integer5);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String seven = formatter.format(calendar.getTime());
        Integer integer6 = dailyReportMapper.getPeriodClick(six,seven);
        userClicked.setSix(integer6);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String eight = formatter.format(calendar.getTime());
        Integer integer7= dailyReportMapper.getPeriodClick(seven,eight);
        userClicked.setSeven(integer7);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String nine = formatter.format(calendar.getTime());
        Integer integer8 = dailyReportMapper.getPeriodClick(eight,nine);
        userClicked.setEight(integer8);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String ten = formatter.format(calendar.getTime());
        Integer integer9 = dailyReportMapper.getPeriodClick(nine,ten);
        userClicked.setNine(integer9);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String eleven = formatter.format(calendar.getTime());
        Integer integer10 = dailyReportMapper.getPeriodClick(ten,eleven);
        userClicked.setTen(integer10);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String twelve = formatter.format(calendar.getTime());
        Integer integer11 = dailyReportMapper.getPeriodClick(eleven,twelve);
        userClicked.setEleven(integer11);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String thirteen = formatter.format(calendar.getTime());
        Integer integer12 = dailyReportMapper.getPeriodClick(twelve,thirteen);
        userClicked.setTwelve(integer12);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String fourteen = formatter.format(calendar.getTime());
        Integer integer13 = dailyReportMapper.getPeriodClick(thirteen,fourteen);
        userClicked.setThirteen(integer13);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String fifteen = formatter.format(calendar.getTime());
        Integer integer14 = dailyReportMapper.getPeriodClick(fourteen,fifteen);
        userClicked.setFourteen(integer14);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String sixteen = formatter.format(calendar.getTime());
        Integer integer15 = dailyReportMapper.getPeriodClick(fifteen,sixteen);
        userClicked.setFifteen(integer15);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String seventeen = formatter.format(calendar.getTime());
        Integer integer16 = dailyReportMapper.getPeriodClick(sixteen,seventeen);
        userClicked.setSixteen(integer16);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String eighteen = formatter.format(calendar.getTime());
        Integer integer17 = dailyReportMapper.getPeriodClick(seventeen,eighteen);
        userClicked.setSeventeen(integer17);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String nineteen = formatter.format(calendar.getTime());
        Integer integer18 = dailyReportMapper.getPeriodClick(eighteen,nineteen);
        userClicked.setEighteen(integer18);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String twenty = formatter.format(calendar.getTime());
        Integer integer19 = dailyReportMapper.getPeriodClick(nineteen,twenty);
        userClicked.setNineteen(integer19);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String twentyOne = formatter.format(calendar.getTime());
        Integer integer20 = dailyReportMapper.getPeriodClick(twenty,twentyOne);
        userClicked.setTwenty(integer20);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String twentyTwo = formatter.format(calendar.getTime());
        Integer integer21 = dailyReportMapper.getPeriodClick(twentyOne,twentyTwo);
        userClicked.setTwentyOne(integer21);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String twentyThree = formatter.format(calendar.getTime());
        Integer integer22 = dailyReportMapper.getPeriodClick(twentyTwo,twentyThree);
        userClicked.setTwentyTwo(integer22);

        calendar.add(Calendar.HOUR_OF_DAY, +1);
        String twentyFour = formatter.format(calendar.getTime());
        Integer integer23 = dailyReportMapper.getPeriodClick(twentyThree,twentyFour);
        userClicked.setTwentyThree(integer23);
        Integer total= integer23+integer22+integer21+integer20+integer19+integer18+integer17+integer16
                +integer15+integer14+integer13+integer12+integer11+integer10+integer9+integer8+integer7
                +integer6+integer5+integer4+integer3+integer2+integer1+integer0;
        userClicked.setTotal(total);

        userClickedMapper.insert(userClicked);

        return ResultInfo.success("记录成功！");
    }

    private void inserRetained(String day, String yesterday, int type) {
        if (type == 1) {
            String startTimeYesterday = DateToStringBeginOrEnd(yesterday, true);
            String endTimeYesterday = DateToStringBeginOrEnd(yesterday, false);

            String startTime = DateToStringBeginOrEnd(day, true);
            String endTime = DateToStringBeginOrEnd(day, false);
            Integer newRegisterCount = dailyReportMapper.getNewRegisterCount(startTime, endTime);
            Integer retainedCount = dailyReportMapper.getRetainedCount(startTime, endTime, startTimeYesterday, endTimeYesterday);
            float rate = (float) retainedCount / newRegisterCount;
            NextDayRetained nextDayRetained = new NextDayRetained();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date date = sdf.parse(day);
                nextDayRetained.setDateTime(date);


                nextDayRetained.setLoginCount(retainedCount);
                nextDayRetained.setRegisterCount(newRegisterCount);
                if (0 == retainedCount) {
                    float f = 0;
                    nextDayRetained.setRate(f);
                } else {
                    nextDayRetained.setRate(rate);
                }
                QueryWrapper<NextDayRetained> wrapper = new QueryWrapper<>();
                wrapper.eq("date_time", date);
                NextDayRetained selectOne = nextDayRetainedMapper.selectOne(wrapper);
                if (null == selectOne) {
                    nextDayRetainedMapper.insert(nextDayRetained);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (type == 2) {
            String startTimeYesterday = DateToStringBeginOrEnd(yesterday, true);
            String endTimeYesterday = DateToStringBeginOrEnd(yesterday, false);

            String startTime = DateToStringBeginOrEnd(day, true);
            String endTime = DateToStringBeginOrEnd(day, false);
            Integer newRegisterCount = dailyReportMapper.getNewRegisterCount(startTime, endTime);
            Integer retainedCount = dailyReportMapper.getRetainedCount(startTime, endTime, startTimeYesterday, endTimeYesterday);
            float rate = (float) retainedCount / newRegisterCount;
            ThreeDayRetained threeDayRetained = new ThreeDayRetained();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date date = sdf.parse(day);
                threeDayRetained.setDateTime(date);
                threeDayRetained.setLoginCount(retainedCount);
                threeDayRetained.setRegisterCount(newRegisterCount);
                if (0 == retainedCount) {
                    float f = 0;
                    threeDayRetained.setRate(f);
                } else {
                    threeDayRetained.setRate(rate);
                }
                QueryWrapper<ThreeDayRetained> wrapper = new QueryWrapper<>();
                wrapper.eq("date_time", date);
                ThreeDayRetained selectOne = threeDayRetainedMapper.selectOne(wrapper);
                if (null == selectOne) {
                    threeDayRetainedMapper.insert(threeDayRetained);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (type == 3) {
            String startTimeYesterday = DateToStringBeginOrEnd(yesterday, true);
            String endTimeYesterday = DateToStringBeginOrEnd(yesterday, false);
            String startTime = DateToStringBeginOrEnd(day, true);
            String endTime = DateToStringBeginOrEnd(day, false);
            Integer newRegisterCount = dailyReportMapper.getNewRegisterCount(startTime, endTime);
            Integer retainedCount = dailyReportMapper.getRetainedCount(startTime, endTime, startTimeYesterday, endTimeYesterday);
            float rate = (float) retainedCount / newRegisterCount;
            SevenDayRetained sevenDayRetained = new SevenDayRetained();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date date = sdf.parse(day);
                sevenDayRetained.setDateTime(date);
                sevenDayRetained.setLoginCount(retainedCount);
                sevenDayRetained.setRegisterCount(newRegisterCount);
                if (0 == retainedCount||newRegisterCount==0) {
                    float f = 0;
                    sevenDayRetained.setRate(f);
                } else {
                    sevenDayRetained.setRate(rate);
                }
                QueryWrapper<SevenDayRetained> wrapper = new QueryWrapper<>();
                wrapper.eq("date_time", date);
                SevenDayRetained selectOne = sevenDayRetainedMapper.selectOne(wrapper);
                if (null == selectOne) {
                    sevenDayRetainedMapper.insert(sevenDayRetained);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (type == 4) {
            String startTimeYesterday = DateToStringBeginOrEnd(yesterday, true);
            String endTimeYesterday = DateToStringBeginOrEnd(yesterday, false);

            String startTime = DateToStringBeginOrEnd(day, true);
            String endTime = DateToStringBeginOrEnd(day, false);
            Integer newRegisterCount = dailyReportMapper.getNewRegisterCount(startTime, endTime);
            Integer retainedCount = dailyReportMapper.getRetainedCount(startTime, endTime, startTimeYesterday, endTimeYesterday);
            float rate = (float) retainedCount / newRegisterCount;
            FifteenDayRetained fifteenDayRetained = new FifteenDayRetained();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date date = sdf.parse(day);
                fifteenDayRetained.setDateTime(date);

                fifteenDayRetained.setLoginCount(retainedCount);
                fifteenDayRetained.setRegisterCount(newRegisterCount);
                if (0 == retainedCount) {
                    float f = 0;
                    fifteenDayRetained.setRate(f);
                } else {
                    fifteenDayRetained.setRate(rate);
                }
                QueryWrapper<FifteenDayRetained> wrapper = new QueryWrapper<>();
                wrapper.eq("date_time", date);
                FifteenDayRetained selectOne = fifteenDayRetainedMapper.selectOne(wrapper);
                if (null == selectOne) {
                    fifteenDayRetainedMapper.insert(fifteenDayRetained);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        if (type == 5) {
            String startTimeYesterday = DateToStringBeginOrEnd(yesterday, true);
            String endTimeYesterday = DateToStringBeginOrEnd(yesterday, false);

            String startTime = DateToStringBeginOrEnd(day, true);
            String endTime = DateToStringBeginOrEnd(day, false);
            Integer newRegisterCount = dailyReportMapper.getNewRegisterCount(startTime, endTime);
            Integer retainedCount = dailyReportMapper.getRetainedCount(startTime, endTime, startTimeYesterday, endTimeYesterday);
            float rate = (float) retainedCount / newRegisterCount;
            ThirtyDayRetained thirtyDayRetained = new ThirtyDayRetained();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date date = sdf.parse(day);
                thirtyDayRetained.setDateTime(date);

                thirtyDayRetained.setLoginCount(retainedCount);
                thirtyDayRetained.setRegisterCount(newRegisterCount);
                if (0 == retainedCount) {
                    float f = 0;
                    thirtyDayRetained.setRate(f);
                } else {
                    thirtyDayRetained.setRate(rate);
                }
                QueryWrapper<ThirtyDayRetained> wrapper = new QueryWrapper<>();
                wrapper.eq("date_time", date);
                ThirtyDayRetained selectOne = thirtyDayRetainedMapper.selectOne(wrapper);
                if (null == selectOne) {
                    thirtyDayRetainedMapper.insert(thirtyDayRetained);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    public String DateToStringBeginOrEnd(String dateStr, Boolean flag) {
        String time = null;
        Date date = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");//注意月份是MM
            date = simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar1 = Calendar.getInstance();
        //获取某一天的0点0分0秒 或者 23点59分59秒
        if (flag) {
            calendar1.setTime(date);
            calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
                    0, 0, 0);
            Date beginOfDate = calendar1.getTime();
            time = dateformat1.format(beginOfDate);
            System.out.println(time);
        } else {
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(date);
            calendar1.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH),
                    23, 59, 59);
            Date endOfDate = calendar1.getTime();
            time = dateformat1.format(endOfDate);
            System.out.println(time);
        }
        return time;
    }
}
