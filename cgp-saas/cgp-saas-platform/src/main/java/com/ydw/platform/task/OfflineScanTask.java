package com.ydw.platform.task;

import com.ydw.platform.model.constant.Constant;
import com.ydw.platform.service.IYdwSchedulejobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class OfflineScanTask implements StartTask{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${offline.scanPeriod}")
	private Long offlineScanPeriod;

	@Autowired
    private IYdwSchedulejobService ydwSchedulejobService;

	@Override
	public void run() {
        ydwSchedulejobService.deleteScheduleJob(Constant.OFFLINE_SCAN_JOB, Constant.OFFLINE_SCAN_JOB);
        HashMap<String,Object> job = new HashMap<>();
        //连接id+上次计费记录id
        job.put("jobName",Constant.OFFLINE_SCAN_JOB);
        job.put("jobGroup",Constant.OFFLINE_SCAN_JOB);
        job.put("server","cgp-saas-platform");
        job.put("path", "/connect/offlineScan");
        job.put("method","get");
        job.put("cron", "0 */"+offlineScanPeriod+" * * * ?");
        ydwSchedulejobService.addScheduleJob(job);
	}

}
