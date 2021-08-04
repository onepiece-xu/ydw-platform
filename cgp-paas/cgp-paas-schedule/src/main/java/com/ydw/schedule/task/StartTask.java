package com.ydw.schedule.task;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public interface StartTask {

	ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

	void run();
}
