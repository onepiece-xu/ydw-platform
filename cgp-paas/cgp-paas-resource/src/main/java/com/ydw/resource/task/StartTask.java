package com.ydw.resource.task;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public interface StartTask {

	ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(2);

	void run();
}
