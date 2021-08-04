package com.ydw.edge.task;

import com.ydw.edge.utils.SpringBeanUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApplicationJob implements CommandLineRunner {
	
	@Override
	public void run(String... args){
		try {
			Map<String, StartTask> beans = SpringBeanUtil.getBeans(StartTask.class);
			for(Map.Entry<String, StartTask> entry : beans.entrySet()){
				StartTask task = entry.getValue();
				task.run();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}