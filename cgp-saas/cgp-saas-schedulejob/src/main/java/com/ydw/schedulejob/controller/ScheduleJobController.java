package com.ydw.schedulejob.controller;


import com.ydw.schedulejob.model.db.ScheduleJob;
import com.ydw.schedulejob.model.vo.ResultInfo;
import com.ydw.schedulejob.service.IScheduleJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 定时任务暴露接口
 * @author xulh
 *
 */
@RestController
@RequestMapping("/scheduleJob")
public class ScheduleJobController {

    @Autowired
    private IScheduleJobService scheduleJobService;
    
    /**  
    * @Title: saveScheduleJob  
    * @Description: 新增定时任务
    * @param @param scheduleJob
    * @param @return    参数
    * @return ResultInfo    返回类型  
    * @throws  
    */
    @PostMapping(value = "/addScheduleJob")
    public ResultInfo saveScheduleJob(@RequestBody ScheduleJob scheduleJob) {
        return scheduleJobService.insert(scheduleJob);
    }
    
    @GetMapping(value = "/listScheduleJob")
    public ResultInfo list(){
        List<ScheduleJob> scheduleJobs = scheduleJobService.queryList();
        return ResultInfo.success(scheduleJobs);
    }
    
    @DeleteMapping(value = "/deleteScheduleJobById")
    public ResultInfo deleteScheduleJob(@RequestParam Long scheduleJobId) {
        scheduleJobService.delete(scheduleJobId);
        return ResultInfo.success();
    }
    
    /**  
    * @Title: deleteScheduleJobByName  
    * @Description: 根据name删除定时任务
    * @param @param scheduleJobName
    * @param @return    参数
    * @return ResultInfo    返回类型  
    * @throws  
    */
    @DeleteMapping(value = "/deleteScheduleJobByNameAndGroup")
    public ResultInfo deleteScheduleJobByNameAndGroup(@RequestParam String scheduleJobName,@RequestParam String scheduleJobGroup) {
        scheduleJobService.deleteScheduleJobByNameAndGroup(scheduleJobName, scheduleJobGroup);
        return ResultInfo.success();
    }
    
    /**  
    * @Title: getScheduleJobByName  
    * @Description: 根据任务名查看配置
    * @param @param scheduleJobName
    * @param @return    参数
    * @return ResultInfo    返回类型  
    * @throws  
    */
    @GetMapping(value = "/getScheduleJobByNameAndGroup")
    public ResultInfo getScheduleJobByName(@RequestParam String scheduleJobName, @RequestParam String scheduleJobGroup) {
        return ResultInfo.success(scheduleJobService.getJobByNameAndGroup(scheduleJobName, scheduleJobGroup));
    }
    
    @PutMapping(value = "/pause-schedule-job/schedulejobid/{scheduleJobId}")
    public ResultInfo pauseScheduleJob(@PathVariable Long scheduleJobId){
        scheduleJobService.pauseJob(scheduleJobId);
        return ResultInfo.success();
    }
    
    @PutMapping(value = "/resume-schedule-job/schedulejobid/{scheduleJobId}")
    public ResultInfo resumeScheduleJob(@PathVariable Long scheduleJobId){
        scheduleJobService.resumeJob(scheduleJobId);
        return ResultInfo.success();
    }
}

