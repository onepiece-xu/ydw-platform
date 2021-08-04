package com.ydw.admin.task;

import com.ydw.admin.model.constant.Constant;
import com.ydw.admin.service.IYdwSchedulejobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class GenerateDailyReport implements  StartTask{
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IYdwSchedulejobService ydwSchedulejobService;

    @Override
    public void run() {
        ydwSchedulejobService.deleteScheduleJob(Constant.GENERATE_DAILY_REPORT,Constant.GENERATE_DAILY_REPORT);
        HashMap<String,Object> job = new HashMap<>();
        //连接id+上次计费记录id
        job.put("jobName",Constant.GENERATE_DAILY_REPORT);
        job.put("jobGroup",Constant.GENERATE_DAILY_REPORT);
        job.put("server","cgp-saas-admin");
        job.put("path", "/dailyReport/generateDailyReport");
        job.put("method","get");
        job.put("cron", "0 10 0 ? * *");//每天0点10分进行
        ydwSchedulejobService.addScheduleJob(job);
    }
}
