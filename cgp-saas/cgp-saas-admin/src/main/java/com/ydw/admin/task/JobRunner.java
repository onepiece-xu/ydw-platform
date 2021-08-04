package com.ydw.admin.task;

import com.ydw.admin.util.SpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class JobRunner implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void run(String... args) throws Exception {
        logger.info("---------");
        try {
            Map<String, StartTask> beans = SpringBeanUtil.getBeans(StartTask.class);
            for ( Map.Entry<String, StartTask> entry:beans.entrySet()){
                StartTask task = entry.getValue();
                task.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
