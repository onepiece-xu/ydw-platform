package com.ydw.platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.platform.dao.UserHangupMapper;
import com.ydw.platform.model.constant.Constant;
import com.ydw.platform.model.db.UserHangup;
import com.ydw.platform.service.IUserHangupService;
import com.ydw.platform.util.DateUtil;
import com.ydw.platform.util.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 用户挂机记录 服务实现类
 * </p>
 *
 * @author xulh
 * @since 2021-07-20
 */
@Service
public class UserHangupServiceImpl extends ServiceImpl<UserHangupMapper, UserHangup> implements IUserHangupService {

    @Autowired
    private FeginClientService feginClientService;

    @Value("${hangup.oneDayMaxTime}")
    private int oneDayMaxTime;

    @Override
    public int getUserOneDaySubHangupDuration(String account){
        QueryWrapper<UserHangup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", account);
        LocalDate now = LocalDate.now();
        LocalDateTime beginDayTime = LocalDateTime.of(now, LocalTime.MIN);
        LocalDateTime endDayTime = LocalDateTime.of(now, LocalTime.MAX);
        queryWrapper.and(o ->
                o.between("begin_time",beginDayTime, endDayTime)
                        .or()
                        .between("end_time",beginDayTime, endDayTime));
        List<UserHangup> userHangups = list(queryWrapper);
        int hangupedDuration = 0;
        if (userHangups != null && !userHangups.isEmpty()){
            for (UserHangup userHangup : userHangups) {
                LocalDateTime beginTime = userHangup.getBeginTime().isBefore(beginDayTime) ? beginDayTime : userHangup.getBeginTime();
                LocalDateTime endTime = userHangup.getEndTime() == null ? LocalDateTime.now() :
                        userHangup.getEndTime().isAfter(endDayTime) ? endDayTime : userHangup.getEndTime();
                hangupedDuration += Duration.between(beginTime, endTime).toMinutes();
            }
        }
        return oneDayMaxTime - hangupedDuration;
    }

    @Override
    public void startHangup(String account, String connectId, int hangupDuration) {
        LocalDateTime nowTime = LocalDateTime.now();
        endHangup(connectId);
        UserHangup userHangup = new UserHangup();
        userHangup.setId(SequenceGenerator.sequence());
        userHangup.setHangupDuration(hangupDuration);
        userHangup.setBeginTime(nowTime);
        userHangup.setConnectId(connectId);
        userHangup.setValid(true);
        userHangup.setUserId(account);
        save(userHangup);//判断是否已经有挂机任务了
        feginClientService.deleteScheduleJob(connectId, Constant.HANGUP_JOB);
        HashMap<String,Object> job = new HashMap<>();
        //连接id+上次计费记录id
        job.put("jobName",connectId);
        job.put("jobGroup",Constant.HANGUP_JOB);
        job.put("server","cgp-saas-platform");
        job.put("path", "/resource/hangupEnd");
        job.put("method","post");
        HashMap<String,Object> params = new HashMap<>();
        params.put("connectId",connectId);
        Date date = DateUtil.addSeconds(new Date(), hangupDuration*60);
        params.put("hangupEndTime",date);
        job.put("params", JSON.toJSONString(params));
        String cron = DateUtil.dateFormatcron(date);
        job.put("cron", cron);
        feginClientService.addScheduleJob(job);

    }

    @Override
    public void endHangup(String connectId){
        LocalDateTime nowTime = LocalDateTime.now();
        UpdateWrapper<UserHangup> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("connect_id", connectId);
        updateWrapper.eq("valid", true);
        updateWrapper.set("end_time", nowTime);
        updateWrapper.set("valid", false);
        update(updateWrapper);
        feginClientService.deleteScheduleJob(connectId, Constant.HANGUP_JOB);
    }

    @Override
    public long getHangupEndDuration(String connectId) {
        QueryWrapper<UserHangup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("connect_id", connectId);
        queryWrapper.eq("valid", true);
        UserHangup one = getOne(queryWrapper);
        if (one == null){
            return -1;
        }else{
            LocalDateTime beginTime = one.getBeginTime();
            long seconds = Duration.between(beginTime, LocalDateTime.now()).getSeconds();
            return one.getHangupDuration() * 60 - seconds;
        }
    }
}
