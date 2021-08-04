package com.ydw.resource.task;

import com.ydw.resource.model.constant.Constant;
import com.ydw.resource.model.db.Devices;
import com.ydw.resource.service.IDeviceService;
import com.ydw.resource.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class RebootingScanTask implements StartTask{

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private IDeviceService deviceService;

    @Value("${rebooting.period}")
    private Long rebootingPeriod;

	@Value("${rebooting.timeout}")
	private Long rebootingTimeOut;

	@Override
	public void run() {
        scheduledExecutorService.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                rebootIngScan();
            }
        }, 60, rebootingPeriod, TimeUnit.SECONDS);
    }

    private void rebootIngScan(){
        Long l = (System.currentTimeMillis() - rebootingTimeOut * 1000);
        Set<Object> zRange = redisUtil.zRange(Constant.ZSET_REBOOTING, Double.MIN_VALUE, l.doubleValue());
        for (Object object : zRange){
            String deviceId = (String)object;
            redisUtil.zRemove(Constant.ZSET_REBOOTING,deviceId);
            Devices devices = deviceService.getById(deviceId);
            if (devices.getStatus() == Constant.DEVICE_STATUS_REBOOTING){
                deviceService.reportStatus(deviceId,Constant.DEVICE_STATUS_ERROR);
            }
        }
    }

    public void enqueue(String deviceId){
        Double aDouble = redisUtil.zScore(Constant.ZSET_REBOOTING, deviceId);
        if (aDouble == null || aDouble == -1D){
            redisUtil.zSet(Constant.ZSET_REBOOTING,deviceId, System.currentTimeMillis());
        }
    }

    public Long dequeue(String deviceId){
        return redisUtil.zRemove(Constant.ZSET_REBOOTING, deviceId);
    }
}
