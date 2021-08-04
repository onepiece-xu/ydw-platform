package com.ydw.admin.task;

import com.ydw.admin.model.constant.Constant;
import com.ydw.admin.service.IYdwSchedulejobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
@Component
public class ClearMission  implements StartTask{
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IYdwSchedulejobService ydwSchedulejobService;
    @Override
    public void run() {
        ydwSchedulejobService.deleteScheduleJob(Constant.CLEAR_MISSION,Constant.CLEAR_MISSION);
        HashMap<String,Object> job = new HashMap<>();
        //连接id+上次计费记录id
        job.put("jobName",Constant.CLEAR_MISSION);
        job.put("jobGroup",Constant.CLEAR_MISSION);
        job.put("server","cgp-saas-admin");
        job.put("path", "/mission/clearMission");
        job.put("method","get");
        job.put("cron", "0 0 1 ? * *");//每天0点进行
        ydwSchedulejobService.addScheduleJob(job);
    }
}
