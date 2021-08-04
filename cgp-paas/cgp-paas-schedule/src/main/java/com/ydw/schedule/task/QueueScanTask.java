package com.ydw.schedule.task;

import com.ydw.schedule.service.IQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class QueueScanTask implements StartTask{

    @Value("${queueScan.period}")
    long queueScanPeriod;

	@Autowired
	private IQueueService queueService;

	@Override
	public void run() {
        scheduledExecutorService.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                queueService.consumeQueue();
            }
        },60 ,queueScanPeriod , TimeUnit.SECONDS);
    }
}
