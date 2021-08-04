package com.ydw.admin.task;

import com.ydw.admin.model.constant.Constant;
import com.ydw.admin.service.IYdwSchedulejobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class GenerateRetainedRate implements StartTask {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IYdwSchedulejobService ydwSchedulejobService;
    @Override
    public void run() {
        ydwSchedulejobService.deleteScheduleJob(Constant.GENERATE_REATAINED_RATE,Constant.GENERATE_REATAINED_RATE);
        HashMap<String,Object> job = new HashMap<>();
        //连接id+上次计费记录id
        job.put("jobName",Constant.GENERATE_REATAINED_RATE);
        job.put("jobGroup",Constant.GENERATE_REATAINED_RATE);
        job.put("server","cgp-saas-admin");
        job.put("path", "/dailyReport/generateRetainedReport");
        job.put("method","get");
        job.put("cron", "0 30 0 ? * *");//每天0点30分进行
        ydwSchedulejobService.addScheduleJob(job);
    }
}
