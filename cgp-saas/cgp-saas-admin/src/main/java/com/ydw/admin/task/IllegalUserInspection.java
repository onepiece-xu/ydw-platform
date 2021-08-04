package com.ydw.admin.task;

import com.ydw.admin.model.constant.Constant;
import com.ydw.admin.service.IYdwSchedulejobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
@Component
public class IllegalUserInspection  implements StartTask{
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private IYdwSchedulejobService ydwSchedulejobService;
    @Override
    public void run() {
        ydwSchedulejobService.deleteScheduleJob(Constant.ILLEGAL_USER_INSPECTION,Constant.ILLEGAL_USER_INSPECTION);
        HashMap<String,Object> job = new HashMap<>();
        //连接id+上次计费记录id
        job.put("jobName",Constant.ILLEGAL_USER_INSPECTION);
        job.put("jobGroup",Constant.ILLEGAL_USER_INSPECTION);
        job.put("server","cgp-saas-admin");
        job.put("path", "/userInfo/IllegalUserInspection");
        job.put("method","get");
        job.put("cron", "0 */15 * * * ?");//每5分进行
        ydwSchedulejobService.addScheduleJob(job);
    }
}
