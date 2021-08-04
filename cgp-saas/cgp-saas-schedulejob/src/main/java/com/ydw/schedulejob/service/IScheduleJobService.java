package com.ydw.schedulejob.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ydw.schedulejob.model.db.ScheduleJob;
import com.ydw.schedulejob.model.vo.ResultInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author heao
 * @since 2019-10-11
 */
public interface IScheduleJobService extends IService<ScheduleJob> {

    public ResultInfo insert(ScheduleJob scheduleJob);

    /** 
    * @Title: delete 
    * @Description: 删除
    * @param @param scheduleJobId    设定文件 
    * @return void    返回类型 
    * @throws 
    */
    public void delete(Long scheduleJobId);

    /**  
    * @Title: deleteJobByName  
    * @Description: TODO
    * @param @param scheduleJobName    参数
    * @return void    返回类型  
    * @throws  
    */
    public void deleteJobByName(String scheduleJobName);
    
    /**  
    * @Title: getJobByName  
    * @Description: 获取定时任务
    * @param @param scheduleJobName    参数
    * @return void    返回类型  
    * @throws  
    */
    public ScheduleJob getJobByName(String scheduleJobName);
   
    /** 
    * @Title: runOnce 
    * @Description: 执行一次任务 
    * @param @param scheduleJobId    设定文件 
    * @return void    返回类型 
    * @throws 
    */
    public void runOnce(Long scheduleJobId);


    /** 
    * @Title: pauseJob 
    * @Description: 暂停 
    * @param @param scheduleJobId    设定文件 
    * @return void    返回类型 
    * @throws 
    */
    public void pauseJob(Long scheduleJobId);

    
    /** 
    * @Title: resumeJob 
    * @Description: 恢复 
    * @param @param scheduleJobId    设定文件 
    * @return void    返回类型 
    * @throws 
    */
    public void resumeJob(Long scheduleJobId);

    /**
     * 获取任务对象
     * 
     * @param scheduleJobId
     * @return
     */
    public ScheduleJob get(Long scheduleJobId);

    /** 
    * @Title: queryList 
    * @Description: 查询任务列表 
    * @param @return    设定文件 
    * @return List<ScheduleJob>    返回类型 
    * @throws 
    */
    public List<ScheduleJob> queryList();

    /**
     * 根据任务名称和任务组获取任务详情
     * @param scheduleJobName
     * @param scheduleJobGroup
     * @return
     */
    ScheduleJob getJobByNameAndGroup(String scheduleJobName, String scheduleJobGroup);

    /**
     * 根据任务名称和任务组删除任务
     * @param scheduleJobName
     * @param scheduleJobGroup
     * @return
     */
    boolean deleteScheduleJobByNameAndGroup(String scheduleJobName, String scheduleJobGroup);
}
