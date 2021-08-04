package com.ydw.message.task;

import com.ydw.message.util.SpringBeanUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class ApplicationJob implements CommandLineRunner{
	
	@Override
	public void run(String... args) throws Exception {
		LocalDateTime t1 = LocalDateTime.now();
		try {
			Map<String, StartTask> beans = SpringBeanUtil.getBeans(StartTask.class);
			for(Map.Entry<String, StartTask> entry : beans.entrySet()){
				StartTask task = entry.getValue();
				task.run();
			}
		} catch (Exception e) {
			System.out.println(Duration.between(t1,LocalDateTime.now()).getSeconds());
			e.printStackTrace();
		}
	}

}
