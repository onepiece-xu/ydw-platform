package com.ydw.charge.task;

import com.ydw.charge.model.constants.Constant;
import com.ydw.charge.service.IYdwSchedulejobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class PeriodChargeTask implements StartTask{

	Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${charge.period}")
	private Long chargePeriod;

	@Autowired
    private IYdwSchedulejobService ydwSchedulejobService;

	@Override
	public void run() {
        ydwSchedulejobService.deleteScheduleJob(Constant.SCHEDULE_CHARGE_PERIODCHARGE);
        HashMap<String,Object> job = new HashMap<>();
        //连接id+上次计费记录id
        job.put("jobName",Constant.SCHEDULE_CHARGE_PERIODCHARGE);
        job.put("jobGroup",Constant.SCHEDULE_CHARGE_GROUPNAME);
        job.put("server","cgp-saas-charge");
        job.put("path", "/charge/doCharge");
        job.put("method","get");
        job.put("cron", "0 */"+chargePeriod+" * * * ?");
        ydwSchedulejobService.addScheduleJob(job);
	}

}
