package com.ydw.resource.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ydw.resource.model.constant.Constant;
import com.ydw.resource.model.vo.ConnectParams;
import com.ydw.resource.service.IConnectService;
import com.ydw.resource.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@Component
public class PreparingScanTask implements StartTask{

    Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisUtil redisUtil;

    @Autowired
    private IConnectService connectService;

    @Value("${preparing.period}")
    private Long preparingPeriod;

	@Value("${preparing.timeout}")
	private Long preparingTimeOut;

	@Override
	public void run() {
        scheduledExecutorService.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                preparIngScan();
            }
        }, 60, preparingPeriod, TimeUnit.SECONDS);
    }

    private void preparIngScan(){
        Long l = (System.currentTimeMillis() - preparingTimeOut * 1000);
        Set<Object> zRange = redisUtil.zRange(Constant.ZSET_PREPARING, Double.MIN_VALUE, l.doubleValue());
        for (Object object : zRange){
            String jsonString = (String)object;
            logger.info("此{}连接已{}秒没有初始化资源", jsonString, preparingTimeOut);
            JSONObject jsonObject = JSON.parseObject(jsonString);
            ConnectParams params = new ConnectParams();
            params.setAccount(jsonObject.getString("account"));
            params.setDeviceId(jsonObject.getString("deviceId"));
            params.setConnectId(jsonObject.getString("connectId"));
            logger.info("此{}连接已{}秒没有初始化资源,开始释放！", jsonString, preparingTimeOut);
            connectService.cancelConnect(params);
        }
    }

    public void enqueue(String account, String connectId, String deviceId){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("account",account);
        jsonObject.put("connectId",connectId);
        jsonObject.put("deviceId",deviceId);
        String jsonString = jsonObject.toJSONString();
        Double aDouble = redisUtil.zScore(Constant.ZSET_PREPARING, jsonString);
        if (aDouble == null || aDouble == -1D){
            redisUtil.zSet(Constant.ZSET_PREPARING,jsonString,System.currentTimeMillis());
        }
    }

    public Long dequeue(String account, String connectId, String deviceId){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("account",account);
        jsonObject.put("connectId",connectId);
        jsonObject.put("deviceId",deviceId);
        String jsonString = jsonObject.toJSONString();
        return redisUtil.zRemove(Constant.ZSET_PREPARING,jsonString);
    }
}
