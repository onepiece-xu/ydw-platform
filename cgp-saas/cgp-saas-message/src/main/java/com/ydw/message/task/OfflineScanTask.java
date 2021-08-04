package com.ydw.message.task;

import com.ydw.message.model.constants.Constant;
import com.ydw.message.service.IYdwSchedulejobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Component
public class OfflineScanTask implements StartTask{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${bind.scanPeriod}")
	private Long bindScanPeriod;

	@Autowired
    private IYdwSchedulejobService ydwSchedulejobService;

	@Transactional
	@Override
	public void run() {
        ydwSchedulejobService.deleteScheduleJob(Constant.TOKEN_BINED_SCAN_JOB);
        HashMap<String,Object> job = new HashMap<>();
        //连接id+上次计费记录id
        job.put("jobName",Constant.TOKEN_BINED_SCAN_JOB);
        job.put("jobGroup",Constant.TOKEN_BINED_SCAN_JOB);
        job.put("server","cgp-saas-message");
        job.put("path", "/message/checkBind");
        job.put("method","get");
        job.put("cron", "0 */"+bindScanPeriod+" * * * ?");
        ydwSchedulejobService.addScheduleJob(job);
	}

}
