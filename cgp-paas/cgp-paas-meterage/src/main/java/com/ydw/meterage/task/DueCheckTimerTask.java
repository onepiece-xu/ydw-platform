//package com.ydw.meterage.task;
//
//import java.time.LocalDateTime;
//import java.util.Map;
//import java.util.TimerTask;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import com.ydw.meterage.model.constant.RedisConstant;
//import com.ydw.meterage.util.HttpUtil;
//import com.ydw.meterage.util.RedisUtil;
//
//@Component
//public class DueCheckTimerTask extends TimerTask{
//	
//	Logger logger = LoggerFactory.getLogger(getClass());
//	
//	@Autowired
//	private RedisUtil redisUtil;
//	
//	@Value("${forcereleaseUrl}")
//	private String forcereleaseUrl;
//
//	@Override
//	public void run() {
//		LocalDateTime now = LocalDateTime.now();
//		Map<Object, Object> map = redisUtil.hmget(RedisConstant.RECORD_MAP_CONNECT_DUE);
//		map.entrySet()
//			.parallelStream()
//			.filter(e -> ((LocalDateTime)e.getValue()).isBefore(now))
//			.forEach(e -> sendCloseSeq((String)e.getKey()));
//	}
//
//	public void sendCloseSeq(String connectId){		
//		HttpUtil.doJsonPost(forcereleaseUrl, null, new String[]{connectId});
//		redisUtil.hdel(RedisConstant.RECORD_MAP_CONNECT_DUE, connectId);
//	}
//}
