package com.ydw.schedulejob.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydw.schedulejob.dao.ScheduleJobMapper;
import com.ydw.schedulejob.job.HttpJob;
import com.ydw.schedulejob.model.db.ScheduleJob;
import com.ydw.schedulejob.model.vo.ResultInfo;
import com.ydw.schedulejob.service.IScheduleJobService;
import com.ydw.schedulejob.util.ScheduleUtils;
import com.ydw.schedulejob.util.SequenceGenerator;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author heao
 * @since 2019-10-11
 */
@Service
@Transactional
public class ScheduleJobServiceImpl extends ServiceImpl<ScheduleJobMapper, ScheduleJob> implements IScheduleJobService {
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Scheduler scheduler;
    
    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    @Override
    public ResultInfo insert(ScheduleJob scheduleJob) {
        JobDetail jobDetail = JobBuilder.newJob(HttpJob.class)
                .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup()).build();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        jobDataMap.put("server", scheduleJob.getServer());
        jobDataMap.put("path", scheduleJob.getPath());
        jobDataMap.put("method", scheduleJob.getMethod());
        jobDataMap.put("headers", scheduleJob.getHeaders());
        jobDataMap.put("params", scheduleJob.getParams());
        if(scheduleJob.getStartTime() != null && scheduleJob.getFrequency() != null){
            ScheduleUtils.createCalendarIntervalJob(scheduler,scheduleJob.getJobName(),scheduleJob.getJobGroup(),
                    scheduleJob.getStartTime(),scheduleJob.getEndTime(),scheduleJob.getFrequency(),jobDetail);
        }else{
            ScheduleUtils.createCronJob(scheduler,scheduleJob.getJobName(),scheduleJob.getJobGroup(),scheduleJob.getCron(),
                    jobDetail);
        }
        scheduleJob.setId(SequenceGenerator.sequence());
        scheduleJobMapper.insert(scheduleJob);
        return ResultInfo.success();
    }

    @Override
    public void delete(Long scheduleJobId) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectById(scheduleJobId);
        //删除运行的任务
        ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
        //删除数据
        scheduleJobMapper.deleteById(scheduleJobId);
    }

    @Override
    public void runOnce(Long scheduleJobId) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectById(scheduleJobId);
        ScheduleUtils.runOnce(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }

    @Override
    public void pauseJob(Long scheduleJobId) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectById(scheduleJobId);
        ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }

    @Override
    public void resumeJob(Long scheduleJobId) {
        ScheduleJob scheduleJob = scheduleJobMapper.selectById(scheduleJobId);
        ScheduleUtils.resumeJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
    }

    @Override
    public ScheduleJob get(Long scheduleJobId) {
        return scheduleJobMapper.selectById(scheduleJobId);
    }

    @Override
    public List<ScheduleJob> queryList() {

        List<ScheduleJob> scheduleJobs = scheduleJobMapper.selectList(null);

        try {
            for (ScheduleJob scheduleJob : scheduleJobs) {

                JobKey jobKey = ScheduleUtils.getJobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                if (CollectionUtils.isEmpty(triggers)) {
                    continue;
                }

                //这里一个任务可以有多个触发器， 但是我们一个任务对应一个触发器，所以只取第一个即可，清晰明了
                Trigger trigger = triggers.iterator().next();

                Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                scheduleJob.setStatus(triggerState.name());

                if (trigger instanceof CalendarIntervalTrigger) {
                    CalendarIntervalTrigger calendarIntervalTrigger = (CalendarIntervalTrigger) trigger;
                    Date startTime = calendarIntervalTrigger.getStartTime();
                    int frequency = calendarIntervalTrigger.getRepeatInterval();
                    scheduleJob.setStartTime(startTime);
                    scheduleJob.setFrequency(frequency);
                }
            }
        } catch (SchedulerException e) {
            logger.error("list schedule job error" + e.getMessage());
        }
        return scheduleJobs;
    }

    @Override
    public void deleteJobByName(String scheduleJobName) {
        QueryWrapper<ScheduleJob> qw = new QueryWrapper<>();
        qw.eq("job_name",scheduleJobName);
        ScheduleJob scheduleJob = scheduleJobMapper.selectOne(qw);
        if(scheduleJob!=null){
            //删除运行的任务
            ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
            //删除数据
            scheduleJobMapper.deleteById(scheduleJob);
        }
        
    }

    /**
     * 根据任务名称和任务组删除任务
     *
     * @param scheduleJobName
     * @param scheduleJobGroup
     * @return
     */
    @Override
    public boolean deleteScheduleJobByNameAndGroup(String scheduleJobName, String scheduleJobGroup) {
        QueryWrapper<ScheduleJob> qw = new QueryWrapper<>();
        qw.eq("job_name",scheduleJobName);
        qw.eq("job_group", scheduleJobGroup);
        ScheduleJob scheduleJob = scheduleJobMapper.selectOne(qw);
        if(scheduleJob!=null){
            //删除运行的任务
            ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());
            //删除数据
            int i = scheduleJobMapper.deleteById(scheduleJob);
            return i > 0;
        }else{
            return false;
        }
    }

    @Override
    public ScheduleJob getJobByName(String scheduleJobName) {
        QueryWrapper<ScheduleJob> qw = new QueryWrapper<>();
        qw.eq("job_name", scheduleJobName);
        return getOne(qw);
    }

    @Override
    public ScheduleJob getJobByNameAndGroup(String scheduleJobName,String scheduleJobGroup) {
        QueryWrapper<ScheduleJob> qw = new QueryWrapper<>();
        qw.eq("job_name", scheduleJobName);
        qw.eq("job_group", scheduleJobGroup);
        return getOne(qw);
    }
    
}
