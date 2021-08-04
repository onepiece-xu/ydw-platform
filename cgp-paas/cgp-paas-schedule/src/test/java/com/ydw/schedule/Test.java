package com.ydw.schedule;

import com.ydw.schedule.model.constant.RedisConstant;
import com.ydw.schedule.model.vo.ConnectVO;
import com.ydw.schedule.model.vo.ResultInfo;
import com.ydw.schedule.service.impl.ConnectServiceImpl;
import com.ydw.schedule.util.RedisUtil;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ScheduleApplication.class})
//由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class Test {
	
	@Autowired
	RedisUtil redisUtil;

	@Autowired
	ConnectServiceImpl connectService;

	@org.junit.Test
    public void test() throws Exception{
		Map<Object, Object> hmget = redisUtil.hmget(RedisConstant.SCH_MAP_CONNECT_DEVICE);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("f:\\SCH_MAP_CONNECT_DEVICE.txt")));
		for (Map.Entry<Object, Object> entry : hmget.entrySet()) {
			bufferedWriter.write((String)entry.getKey());
			bufferedWriter.newLine();
			bufferedWriter.write((String)entry.getValue());
			bufferedWriter.newLine();
			bufferedWriter.newLine();
		}
		bufferedWriter.flush();
		bufferedWriter = new BufferedWriter(new FileWriter(new File("f:\\SCH_MAP_DEVICE_CONNECT.txt")));
		hmget = redisUtil.hmget(RedisConstant.SCH_MAP_DEVICE_CONNECT);
		for (Map.Entry<Object, Object> entry : hmget.entrySet()) {
			bufferedWriter.write((String)entry.getKey());
			bufferedWriter.newLine();
			bufferedWriter.write((String)entry.getValue());
			bufferedWriter.newLine();
			bufferedWriter.newLine();
		}

		bufferedWriter.flush();
		bufferedWriter = new BufferedWriter(new FileWriter(new File("f:\\que_str_connect_account.txt")));
        Set<String> keys = redisUtil.getKeys("que_str_connect_account*");
		for (String key : keys) {
			String connectId = key.split(":")[1];
			bufferedWriter.write(connectId);
			bufferedWriter.newLine();
			bufferedWriter.write((String)redisUtil.get(key));
			bufferedWriter.newLine();
			bufferedWriter.newLine();
		}
		Set<String> connectIds = new HashSet<>();
		bufferedWriter.flush();
        bufferedWriter = new BufferedWriter(new FileWriter(new File("f:\\que_str_account_connect.txt")));
        keys = redisUtil.getKeys("que_str_account_connect*");
        for (String key : keys) {
            bufferedWriter.write(key.split(":")[1]);
            bufferedWriter.newLine();
            String connectId = ((ConnectVO)redisUtil.get(key)).getConnectId();
            connectIds.add(connectId);
            bufferedWriter.write(connectId);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
		System.out.println(connectIds.toString());
	}
 
}
