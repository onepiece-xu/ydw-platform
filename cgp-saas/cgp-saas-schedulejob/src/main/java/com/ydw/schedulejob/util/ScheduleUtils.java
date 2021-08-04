package com.ydw.schedulejob.util;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 定时任务工具类
 */
public class ScheduleUtils {

    /** 日志对象 */
    public static Logger logger =  LoggerFactory.getLogger(ScheduleUtils.class);

    /** 
    * @Title: getTriggerKey 
    * @Description: 获取触发器key 
    * @param @param jobName
    * @param @param jobGroup
    * @param @return    设定文件 
    * @return TriggerKey    返回类型 
    * @throws 
    */
    public static TriggerKey getTriggerKey(String jobName, String jobGroup) {
        return TriggerKey.triggerKey(jobName, jobGroup);
    }

    /** 
    * @Title: getTrigger 
    * @Description: 获取表达式触发器 
    * @param @param scheduler
    * @param @param jobName
    * @param @param jobGroup
    * @param @return    设定文件 
    * @return Trigger    返回类型 
    * @throws 
    */
    public static Trigger getTrigger(Scheduler scheduler, String jobName, String jobGroup) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            return scheduler.getTrigger(triggerKey);
        } catch (SchedulerException e) {
        	logger.error("获取定时任务CronTrigger出现异常", e);
            throw new RuntimeException("获取定时任务CronTrigger出现异常");
        }
    }


    /** 
    * @Title: createScheduleJob 
    * @Description: 创建定时任务 (以起始时间和执行频率创建)
    * @param @param scheduler
    * @param @param scheduleJob
    * @param @param jobDetail    设定文件 
    * @return void    返回类型 
    * @throws 
    */
    public static void createCalendarIntervalJob(Scheduler scheduler, String jobName, String jobGroup,
			Date startTime, Date endTime, int frequency, JobDetail jobDetail) {
		CalendarIntervalScheduleBuilder builder = CalendarIntervalScheduleBuilder.calendarIntervalSchedule()
				.withIntervalInMinutes(frequency);
		CalendarIntervalTrigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(jobName, jobGroup)
				.startAt(startTime)
				.endAt(endTime)
				.withSchedule(builder)
				.build();
        try {
        	//创建定时任务
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
        	logger.error("创建定时任务失败", e);
        }
    }

    /** 
    * @Title: createScheduleJobByCron 
    * @Description: 创建定时任务 (以cron表达式创建)
    * @param @param scheduler
    * @param @param scheduleJob
    * @param @param jobDetail    设定文件 
    * @return void    返回类型 
    * @throws 
    */
    public static void createCronJob(Scheduler scheduler, String jobName, String jobGroup,
			String cron, JobDetail jobDetail) {
    	try {
    		//cron表达式构建任务触发器
    		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
    		CronTrigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(jobName, jobGroup)
    				.withSchedule(scheduleBuilder)
					.build();
        	//创建定时任务
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
        	logger.error("创建定时任务失败", e);
        }
    	
    }
    /** 
    * @Title: runOnce 
    * @Description: 运行一次任务 
    * @param @param scheduler
    * @param @param jobName
    * @param @param jobGroup    设定文件 
    * @return void    返回类型 
    * @throws 
    */
    public static void runOnce(Scheduler scheduler, String jobName, String jobGroup) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
        	logger.error("运行一次定时任务失败", e);
        }
    }

    /** 
    * @Title: pauseJob 
    * @Description: 暂停任务 
    * @param @param scheduler
    * @param @param jobName
    * @param @param jobGroup    设定文件 
    * @return void    返回类型 
    * @throws 
    */
    public static void pauseJob(Scheduler scheduler, String jobName, String jobGroup) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
        	logger.error("暂停定时任务失败", e);
        }
    }

    /** 
    * @Title: resumeJob 
    * @Description: 恢复任务 
    * @param @param scheduler
    * @param @param jobName
    * @param @param jobGroup    设定文件 
    * @return void    返回类型 
    * @throws 
    */
    public static void resumeJob(Scheduler scheduler, String jobName, String jobGroup) {
        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
        	logger.error("重启定时任务失败", e);
        }
    }

    /** 
    * @Title: getJobKey 
    * @Description: 获取jobKey 
    * @param @param jobName
    * @param @param jobGroup
    * @param @return    设定文件 
    * @return JobKey    返回类型 
    * @throws 
    */
    public static JobKey getJobKey(String jobName, String jobGroup) {
        return JobKey.jobKey(jobName, jobGroup);
    }


    /**
     * 删除定时任务
     *
     * @param scheduler
     * @param jobName
     * @param jobGroup
     */
    public static void deleteScheduleJob(Scheduler scheduler, String jobName, String jobGroup) {
        try {
            scheduler.deleteJob(getJobKey(jobName, jobGroup));
        } catch (SchedulerException e) {
        	logger.error("删除定时任务失败", e);
        }
    }
    
}
