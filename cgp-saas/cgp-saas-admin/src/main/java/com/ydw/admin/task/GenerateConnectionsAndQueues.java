package com.ydw.admin.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ydw.admin.dao.JobConfigMapper;
import com.ydw.admin.model.constant.Constant;
import com.ydw.admin.model.db.JobConfig;
import com.ydw.admin.service.IYdwSchedulejobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class GenerateConnectionsAndQueues implements StartTask {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IYdwSchedulejobService ydwSchedulejobService;
    @Autowired
    private JobConfigMapper jobConfigMapper;
    @Override
    public void run() {
        QueryWrapper<JobConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("name","GenerateConnectionsAndQueues");
        JobConfig jobConfig = jobConfigMapper.selectOne(wrapper);
        String minute = jobConfig.getMinute();
        logger.info("-----------------每隔---------"+minute+"+--------分钟---------------记录-");
        logger.error("-----------------每隔---------"+minute+"+--------分钟---------------记录-");
        ydwSchedulejobService.deleteScheduleJob(Constant.GENERATE_CONNECTIONS_AND_QUEUES, Constant.GENERATE_CONNECTIONS_AND_QUEUES);
        HashMap<String,Object> job = new HashMap<>();
        //
        job.put("jobName",Constant.GENERATE_CONNECTIONS_AND_QUEUES);
        job.put("jobGroup",Constant.GENERATE_CONNECTIONS_AND_QUEUES);
        job.put("server","cgp-saas-admin");
        job.put("path", "/dailyReport/generateConnectionsAndQueues");
        job.put("method","get");
//        job.put("cron", "0 30 0 ? * *");//每天0点30分进行
        job.put("cron", "0 0/"+minute+" * * * ?");//每5分钟进行
        ydwSchedulejobService.addScheduleJob(job);
    }
}
