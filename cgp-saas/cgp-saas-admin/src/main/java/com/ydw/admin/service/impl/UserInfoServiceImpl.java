package com.ydw.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.admin.dao.*;
import com.ydw.admin.model.db.*;
import com.ydw.admin.model.enums.MessageTopicEnum;
import com.ydw.admin.model.vo.*;
import com.ydw.admin.service.*;
import com.ydw.admin.util.SequenceGenerator;
import io.netty.util.internal.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hea
 * @since 2020-10-30
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private IYdwRechargeService iYdwRechargeService;
    @Autowired
    private IYdwChargeService ydwChargeService;
    @Value("${sms.appId}")
    private int appId;
    @Value("${sms.appKey}")
    private String appKey;
    @Value("${sms.defaultPhone1}")
    private String defaultPhone1;
    @Value("${sms.defaultPhone2}")
    private String defaultPhone2;
    @Value("${sms.defaultPhone3}")
    private String defaultPhone3;
    @Value("${sms.sign}")
    private String smsSign;

    @Value("${sms.templateIds.illegalUserInspectionId}")
    private int illegalUserInspectionId;

    @Autowired
    private ISMSService ismsService;
    @Autowired
    private IUserPayService userPayService;

    @Autowired
    private RechargeMapper rechargeMapper;

    @Autowired
    private ConnectMapper connectMapper;
    @Autowired
    private PostMessageMapper postMessageMapper;
    @Autowired
    private AwardUserMapper awardUserMapper;
    @Autowired
    private IUserChannelService userChannelService;

    @Autowired
    private IWithdrawRecordService withdrawRecordService;
    @Autowired
    private  IYdwMessageService iYdwMessageService;
    @Autowired
    private IYdwAuthenticationService ydwAuthenticationService;
    @Autowired
    private MessageTemplateMapper messageTemplateMapper;


    @Value("${url.paasApi}")
    private String paasApi;


    @Override
    public ResultInfo getUserList(String mobileNumber, String search, Page buildPage) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.ne("mobile_number", "");
        wrapper.ne("status",2);
        if(!StringUtil.isNullOrEmpty(search)){
            wrapper.like("mobile_number", search);
        }
        wrapper.orderByDesc("register_time");
        Page page = userInfoMapper.selectPage(buildPage, wrapper);
        List<UserInfo> records = page.getRecords();
        List<UserListVO> list = new ArrayList<>();
        for (UserInfo u : records) {
            String userId = u.getId();
            Integer status = u.getStatus();
            UserListVO userListVO = new UserListVO();
            userListVO.setUserId(userId);
            String leftTime = ydwChargeService.getUserUseableTime(userId);
            JSONObject jsonObject = JSONObject.parseObject(leftTime);
            Object data = jsonObject.get("data");
            userListVO.setStatus(status);
            userListVO.setTimeLeft(data.toString());
            userListVO.setUserId(userId);
            userListVO.setMobileNumber(u.getMobileNumber());
            userListVO.setNickName(u.getNickname());
            userListVO.setRegisterTime(u.getRegisterTime());
            list.add(userListVO);
        }
        page.setRecords(list);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo getDistributionUserList(String search, Page buildPage) {
        IPage<DistributionUserVO> page = userInfoMapper.getDistributionUserList(buildPage, search);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo getDistributionRecommender(String userId) {
        DistributionUserVO vo = userInfoMapper.getDistributionRecommender(userId);
        return ResultInfo.success(vo);
    }

    @Override
    public ResultInfo getDistributionInferior(String userId, Page buildPage) {
        IPage<DistributionUserVO> page = userInfoMapper.getDistributionInferior(buildPage, userId);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo getOnlineList(String mobileNumber, String search, Integer client, Page buildPage) {

        Page<OnlineUsersVO> onlineList = userInfoMapper.getOnlineList(search, client, buildPage);

        return ResultInfo.success(onlineList);
    }

    @Override
    public ResultInfo getOnlineListByEnterprise(String enterpriseId, String search, Integer client, Page buildPage) {
        QueryWrapper<UserChannel> qw = new QueryWrapper<>();
        qw.eq("enterprise_id", enterpriseId);
        qw.eq("valid", true);
        List<UserChannel> list = userChannelService.list(qw);
        List<String> channelIds = list.stream().map(UserChannel::getChannelId).collect(Collectors.toList());
        Page<OnlineUsersVO> onlineList = userInfoMapper.getOnlineListByEnterprise(search, client, channelIds, buildPage);
        return ResultInfo.success(onlineList);
    }

    @Override
    public ResultInfo getNewRegisterByEnterprise(String enterpriseId, String startTime, String endTime, Page buildPage) {
        QueryWrapper<UserChannel> qw = new QueryWrapper<>();
        qw.eq("enterprise_id", enterpriseId);
        qw.eq("valid", true);
        List<UserChannel> list = userChannelService.list(qw);
        List<String> channelIds = list.stream().map(UserChannel::getChannelId).collect(Collectors.toList());
        LocalDateTime startDateTime = StringUtils.isNotBlank(startTime) ? LocalDateTime.of(LocalDate.parse(startTime), LocalTime.MIN) : null;
        LocalDateTime endDateTime = StringUtils.isNotBlank(endTime) ? LocalDateTime.of(LocalDate.parse(endTime), LocalTime.MAX) : null;
        Page<HashMap<String,Object>> newRegisters = userInfoMapper.getNewRegisterByEnterprise(startDateTime, endDateTime, channelIds, buildPage);
        return ResultInfo.success(newRegisters);
    }

    @Override
    public ResultInfo getNewRegisterCountByEnterprise(String enterpriseId, String startTime, String endTime) {
        QueryWrapper<UserChannel> qw = new QueryWrapper<>();
        qw.eq("enterprise_id", enterpriseId);
        qw.eq("valid", true);
        List<UserChannel> list = userChannelService.list(qw);
        List<String> channelIds = list.stream().map(UserChannel::getChannelId).collect(Collectors.toList());
        QueryWrapper<UserInfo> qw1 = new QueryWrapper<>();
        qw1.eq("status",0);
        qw1.in("channel_id", channelIds);
        if (StringUtils.isNotBlank(startTime)){
            LocalDateTime startDateTime = LocalDateTime.of(LocalDate.parse(startTime), LocalTime.MIN);
            qw1.gt("register_time", startDateTime);
        }
        if (StringUtils.isNotBlank(endTime)){
            LocalDateTime endDateTime = LocalDateTime.of(LocalDate.parse(endTime), LocalTime.MAX);
            qw1.lt("register_time", endDateTime);
        }
        Integer integer = userInfoMapper.selectCount(qw1);
        return ResultInfo.success(integer);
    }

    @Override
    public ResultInfo getRechargeListByEnterprise(String enterpriseId, String search, Page buildPage) {
        QueryWrapper<UserChannel> qw = new QueryWrapper<>();
        qw.eq("enterprise_id", enterpriseId);
        qw.eq("valid", true);
        List<UserChannel> list = userChannelService.list(qw);
        List<String> channelIds = list.stream().map(UserChannel::getChannelId).collect(Collectors.toList());
        Page<RechargeListVO> rechargeList = rechargeMapper.getRechargeListByEnterprise(buildPage, search, channelIds);
        return ResultInfo.success(rechargeList);
    }

    @Override
    public ResultInfo getRechargeCountByEnterprise(String enterpriseId, String search) {
        QueryWrapper<UserChannel> qw = new QueryWrapper<>();
        qw.eq("enterprise_id", enterpriseId);
        qw.eq("valid", true);
        List<UserChannel> list = userChannelService.list(qw);
        List<String> channelIds = list.stream().map(UserChannel::getChannelId).collect(Collectors.toList());
        RechargeSummaryVO rechargeCountByEnterprise = rechargeMapper.getRechargeCountByEnterprise(search, channelIds);
        return ResultInfo.success(rechargeCountByEnterprise);
    }

    @Override
    public ResultInfo rechargeList(String mobileNumber, String startTime, String endTime,String search,  Page buildPage) {
            if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime)){
                startTime = DateToStringBeginOrEnd(startTime, true);
                endTime= DateToStringBeginOrEnd(endTime, false);
            }
            Page<RechargeListVO> rechargeList = rechargeMapper.getRechargeList(buildPage,startTime,endTime, search);
            return ResultInfo.success(rechargeList);
        }


    @Override
    public ResultInfo chargeList(Integer client, String search, Page buildPage) {

        Page<ChargeListVO> chargeList = userInfoMapper.getChargeList(client,search, buildPage);
        List<ChargeListVO> records = chargeList.getRecords();
        for (ChargeListVO vo: records){
            String customId = vo.getCustomerId();
            String leftTime = ydwChargeService.getUserUseableTime(customId);
            JSONObject jsonObject = JSONObject.parseObject(leftTime);
            Object data = jsonObject.get("data");
            vo.setTimeLeft(data.toString());
            String endTime = vo.getEndTime();
            String beginTime =vo.getBeginTime();
            String diff = "";
            try {
//                String date1 = dealDateFormat(beginTime);
//                String date2 = dealDateFormat(endTime);
                diff = diff(endTime, beginTime);
                vo.setOnlineTime(diff);
            } catch (Exception e) {
                e.printStackTrace();
            }
            vo.setBeginTime(beginTime);
            vo.setEndTime(endTime);

        }
        chargeList.setRecords(records);
        return ResultInfo.success(chargeList);
    }

    private String diff(String date1, String date2) throws ParseException {
            String differenceFormat = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//yyyy-mm-dd, 会出现时间不对, 因为小写的mm是代表: 秒
            Date dateTime1 = sdf.parse(date1);
            Date dateTime2 = sdf.parse(date2);
            long difference = dateTime1.getTime() - dateTime2.getTime();
//            System.out.println("相差毫秒数：" + difference);
            long days = difference / (1000 * 60 * 60 * 24);
            long hours = (difference % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (difference % (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (difference % (1000 * 60)) / 1000;
            differenceFormat = days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds ";
//            System.out.println("相差时间：" + differenceFormat);
            String min = (difference / (1000 * 60)) + "";
            return min;
    }

    public static String dealDateFormat(String oldDate) {
        Date date1 = null;
        DateFormat df2 = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return df2.format(date1);
    }


    @Override
    public ResultInfo getUserPay(String userId) {
        QueryWrapper<UserPay> qw = new QueryWrapper<>();
        qw.eq("payee", userId);
        qw.eq("valid",true);
        List<UserPay> list = userPayService.list(qw);
        return ResultInfo.success(list);
    }

    @Override
    public ResultInfo rechargeSummary( String startTime, String endTime,String search) {
        if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime)){
            startTime = DateToStringBeginOrEnd(startTime, true);
            endTime= DateToStringBeginOrEnd(endTime, false);
        }
        RechargeSummaryVO vo = rechargeMapper.rechargeSummary(startTime,endTime,search);
        return ResultInfo.success(vo);
    }

    @Override
    public ResultInfo chargeSummary(String search) {
        ChargeSummaryVO vo = connectMapper.chargeSummary(search);
        return ResultInfo.success(vo);
    }

    @Override
    public ResultInfo getNewUserList(String search, String startTime, String endTime,Page buildPage) {
        startTime = DateToStringBeginOrEnd(startTime, true);

        endTime= DateToStringBeginOrEnd(endTime, false);

        Page<UserListVO> newUserList = userInfoMapper.getNewUserList(search, startTime, endTime,buildPage);
        List<UserListVO> records = newUserList.getRecords();
        List<UserListVO> list = new ArrayList<>();
        for (UserListVO u : records) {
            String userId = u.getUserId();
            UserListVO userListVO = new UserListVO();
            userListVO.setUserId(userId);
            //查询用户剩余时间
//            String leftTime = ydwChargeService.getUserUseableTime(userId);
//            JSONObject jsonObject = JSONObject.parseObject(leftTime);
//            Object data = jsonObject.get("data");
//            userListVO.setTimeLeft(data.toString());

            userListVO.setUserId(userId);
            userListVO.setMobileNumber(u.getMobileNumber());
            userListVO.setNickName(u.getNickName());
            userListVO.setRegisterTime(u.getRegisterTime());
            list.add(userListVO);
        }
        newUserList.setRecords(list);
        return ResultInfo.success(newUserList);
    }

    @Override
    public ResultInfo getNewUserRecharge(String startTime, String endTime) {
        startTime = DateToStringBeginOrEnd(startTime, true);
        endTime= DateToStringBeginOrEnd(endTime, false);
        String newUserRecharge = userInfoMapper.getNewUserRecharge(startTime, endTime);
        if(StringUtils.isBlank(newUserRecharge)){
            newUserRecharge="0";
        }
        return ResultInfo.success("",newUserRecharge);
    }

    @Override
    public ResultInfo getUserTotalRecharge(String startTime, String endTime) {
        startTime = DateToStringBeginOrEnd(startTime, true);
        endTime= DateToStringBeginOrEnd(endTime, false);
        String userTotalRecharge = userInfoMapper.getUserTotalRecharge(startTime, endTime);
        if(StringUtils.isBlank(userTotalRecharge)){
            userTotalRecharge="0";
        }
        return ResultInfo.success("",userTotalRecharge);
    }

    @Override
    public ResultInfo getOldUserRecharge(String startTime, String endTime) {
        startTime = DateToStringBeginOrEnd(startTime, true);
        endTime= DateToStringBeginOrEnd(endTime, false);
        String oldUserRecharge = userInfoMapper.getOldUserRecharge(startTime, endTime);
        if(StringUtils.isBlank(oldUserRecharge)){
            oldUserRecharge="0";
        }
        return ResultInfo.success("",oldUserRecharge);
    }

    @Override
    public ResultInfo getUserActivity(String startTime, String endTime) {
        List<String> timeList = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            long s = Long.valueOf(sdf.parse(startTime).getTime());
            long e = Long.valueOf(sdf.parse(endTime).getTime());
            //只有结束时间大于开始时间时才进行查询
            if (s < e) {
                timeList = findDates(startTime, endTime);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (String time : timeList) {
            System.out.println(time);
        }

        JSONObject json = new JSONObject();
        json.put("time", timeList);
        List<Object> data = new ArrayList<>();
        for (int i = 0; i < timeList.size(); i++) {
            String t1 = timeList.get(i);
            startTime = DateToStringBeginOrEnd(t1, true);
            endTime= DateToStringBeginOrEnd(t1, false);
            String userActivity = userInfoMapper.getUserActivity(startTime, endTime);
            ActivityVO activityVO = new ActivityVO();
            activityVO.setDaytime(t1);
            activityVO.setPercent(userActivity);
            data.add(activityVO);
        }
        json.put("data",data);

        return ResultInfo.success(json);
    }


    public static List<String> findDates(String stime, String etime)
            throws ParseException {
        List<String> allDate = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date dBegin = sdf.parse(stime);
        Date dEnd = sdf.parse(etime);
        allDate.add(sdf.format(dBegin));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            allDate.add(sdf.format(calBegin.getTime()));
        }
        return allDate;
    }

    public String DateToStringBeginOrEnd(String dateStr,Boolean flag) {
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
        }else{
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

    @Override
    public ResultInfo getChannelBalanceByEnterprise(String enterpriseId) {
        QueryWrapper<UserChannel> qw = new QueryWrapper<>();
        qw.eq("enterprise_id", enterpriseId);
        qw.eq("valid", true);
        UserChannel userChannel = userChannelService.getOne(qw);
        return ResultInfo.success("获取渠道商余额成功！",userChannel.getBalance());
    }

    @Override
    public ResultInfo applyWithdrawByEnterprise(String enterpriseId, BigDecimal amount) {
        QueryWrapper<UserChannel> qw = new QueryWrapper<>();
        qw.eq("enterprise_id", enterpriseId);
        qw.eq("valid", true);
        UserChannel userChannel = userChannelService.getOne(qw);
        WithdrawRecord wr = new WithdrawRecord();
        wr.setStatus(0);
        wr.setApprovalTime(new Date());
        wr.setCreateTime(new Date());
        wr.setId(SequenceGenerator.sequence());
        wr.setPayee(userChannel.getChannelId());
        QueryWrapper<UserPay> qw1 = new QueryWrapper<>();
        qw1.eq("payee", enterpriseId);
        qw1.eq("valid", true);
        UserPay userPay = userPayService.getOne(qw1);
        wr.setPayId(userPay.getId());
        wr.setWithdrawAmount(amount);
        withdrawRecordService.save(wr);
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getWithdrawRecordByEnterprise(String enterpriseId, Integer status, String beginDate, String endDate, Page page) {
        QueryWrapper<UserChannel> qw = new QueryWrapper<>();
        qw.eq("enterprise_id", enterpriseId);
        qw.eq("valid", true);
        List<UserChannel> list = userChannelService.list(qw);
        List<String> channelIds = list.stream().map(UserChannel::getChannelId).collect(Collectors.toList());
        QueryWrapper<WithdrawRecord> qw1 = new QueryWrapper<>();
        qw1.in("payee", channelIds);
        if (status != null){
            qw1.gt("status", status);
        }
        if (StringUtils.isNotBlank(beginDate)){
            LocalDateTime startDateTime = LocalDateTime.of(LocalDate.parse(beginDate), LocalTime.MIN);
            qw1.gt("create_time", startDateTime);
        }
        if (StringUtils.isNotBlank(endDate)){
            LocalDateTime endDateTime = LocalDateTime.of(LocalDate.parse(endDate), LocalTime.MAX);
            qw1.lt("create_time", endDateTime);
        }
        qw1.orderByDesc("create_time");
        page = withdrawRecordService.page(page, qw1);
        return ResultInfo.success(page);
    }

    @Override
    public ResultInfo getUserPayByEnterprise(String enterpriseId) {
        QueryWrapper<UserPay> qw = new QueryWrapper<>();
        qw.eq("payee", enterpriseId);
        qw.eq("valid", true);
        UserPay userPay = userPayService.getOne(qw);
        String payAccount = userPay == null ? null : userPay.getPayAccount();
        return ResultInfo.success("获取渠道商的支付宝账号成功！",payAccount);
    }

    @Override
    public ResultInfo bindBalancePayByEnterprise(String enterpriseId, String payAccount) {
        QueryWrapper<UserPay> qw = new QueryWrapper<>();
        qw.eq("payee", enterpriseId);
        qw.eq("valid", true);
        UserPay userPay = userPayService.getOne(qw);
        if (userPay == null){
            QueryWrapper<UserChannel> uw = new QueryWrapper<>();
            uw.eq("enterprise_id", enterpriseId);
            uw.eq("valid", true);
            UserChannel one = userChannelService.getOne(uw);
            userPay = new UserPay();
            userPay.setCreateTime(new Date());
            userPay.setId(SequenceGenerator.sequence());
            userPay.setPayeeName(one.getEnterpriseName());
            userPay.setPayType(1);
            userPay.setIsdefault(true);
            userPay.setPayAccount(payAccount);
            userPay.setPayee(enterpriseId);
            userPayService.save(userPay);
        }else{
            userPay.setPayAccount(payAccount);
            userPayService.updateById(userPay);
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo getRechargeCardsByType(int type) {
        ResultInfo rechargeCardsByType = iYdwRechargeService.getRechargeCardsByType(type);
        String jsonString = JSON.toJSONString(rechargeCardsByType.getData());
        List<RechargeCard> data = null;
        try {
            data = JSONObject.parseArray(jsonString, RechargeCard.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultInfo.success(data);
    }

    @Override
    public ResultInfo awardTime(HashMap<String, String> params) {
        ResultInfo resultInfo = iYdwRechargeService.addCardBag(params);
        if(resultInfo.getCode()==200){
            return ResultInfo.success();
        }
        return ResultInfo.fail("奖励失败！");
    }

    @Override
    public ResultInfo sendMessage(MsgVO msg) {
        String userId = msg.getUserId();
        String data = msg.getContent();
        String title = msg.getTitle();
        Msg sendmsg = new Msg();
        JSONObject object = new JSONObject();
        object.put("content",data);
        object.put("title",title);
        sendmsg.setData(object.toJSONString());
        List<String> userOnlineTokens = getUserOnlineTokens(userId);
        sendmsg.setReceivers(userOnlineTokens);
        sendmsg.setDealType(0);
        sendmsg.setType(MessageTopicEnum.NOTICE.getTopic());
        try {
            String sendMsg = iYdwMessageService.sendMsg(sendmsg);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(title+"-----------消息发送失败-----------");
        }
        return ResultInfo.success();
    }

    @Override
    public ResultInfo adminAwardTime(HashMap<String, String> params) {
        String cardId = params.get("cardId");

        QueryWrapper<AwardUser> wrapper = new QueryWrapper<>();
        List<AwardUser> awardUserList = awardUserMapper.selectList(wrapper);

        QueryWrapper<MessageTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",13);
        MessageTemplate messageTemplate = messageTemplateMapper.selectOne(queryWrapper);

        for(AwardUser mobile:awardUserList){
            ResultInfo resultInfo = null;
            String id="";
            String moblieNumber = mobile.getMoblieNumber();
            QueryWrapper<UserInfo> wp = new QueryWrapper<>();
            wp.eq("mobile_number",moblieNumber);
            UserInfo userInfo = userInfoMapper.selectOne(wp);
            try {
            id = userInfo.getId();
            HashMap<String, String> map = new HashMap<>();
            map.put("cardId",cardId);
            map.put("userId",id);
            resultInfo = iYdwRechargeService.addCardBag(map);
                String content = messageTemplate.getContent();
                String title = messageTemplate.getTitle();
                if(StringUtils.isNotEmpty(content)){
                    PostMessage postMessage = new PostMessage();
                    postMessage.setContent(content);
                    postMessage.setTitle(title);
                    postMessage.setUserId(id);
                    postMessage.setCreateTime(new Date());
                    postMessage.setToAll(true);
                    postMessage.setHasRead(false);
                    postMessage.setType(2);
                    postMessageMapper.insert(postMessage);
                }

                if(resultInfo.getCode()!=200){
                    logger.error("用户------------ID"+id+"奖励卡  cardId-----"+cardId+"失败！！");
                }
            } catch (Exception e) {
                logger.error("手机用户------------："+moblieNumber+"奖励卡  cardId-----"+cardId+"失败！！");
                e.printStackTrace();
            }

        }

        return ResultInfo.success();
    }

    @Override
    public ResultInfo sendMessageAll(MsgVO msg) {
//        String userId = msg.getUserId();
        String data = msg.getContent();
        String title = msg.getTitle();
        Msg sendmsg = new Msg();
        JSONObject object = new JSONObject();
        object.put("content",data);
        object.put("title",title);
        sendmsg.setData(object.toJSONString());
        List<String> awardUsers = userInfoMapper.getAllUser();
        for(String  userId:awardUsers) {

            try {
                List<String> userOnlineTokens = getUserOnlineTokens(userId);
                sendmsg.setReceivers(userOnlineTokens);
                sendmsg.setDealType(0);
                sendmsg.setType(MessageTopicEnum.NOTICE.getTopic());
                //停5秒执行
                Thread.sleep(1500);
                if(userOnlineTokens.size()!=0){
                    String sendMsg = iYdwMessageService.sendMsg(sendmsg);
                }
            } catch (Exception e) {
                logger.error(title + "-----------消息发送失败----------userdId-"+userId);
                e.printStackTrace();
            }

        }

        return ResultInfo.success();
    }

    @Override
    public ResultInfo IllegalUserInspection() {
        //查询在线用户ip是否超过3个ip相同的
        List<IPCountVO> ipList = userInfoMapper.getOnlineListInfo();
        for(IPCountVO vo:ipList){
            Integer count = vo.getCount();
            if(count>=3){
                //如果超过三个，则发短信给管理员
                try {
                    ismsService.sendSms(defaultPhone1,illegalUserInspectionId,null);
                    ismsService.sendSms(defaultPhone2,illegalUserInspectionId,null);
                    ismsService.sendSms(defaultPhone3,illegalUserInspectionId,null);
                    return ResultInfo.success();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ResultInfo.success();



    }

    private List<String> getUserOnlineTokens(String userId){
        List<String> tokens = new ArrayList<>();
        String userOnlineTokens = ydwAuthenticationService.getUserOnlineTokens(userId);
        JSONObject jsonObject = JSON.parseObject(userOnlineTokens);
        if (jsonObject.getIntValue("code") == 200){
            JSONArray data = jsonObject.getJSONArray("data");
            tokens = data.toJavaList(String.class);
        }
        return tokens;
    }
}