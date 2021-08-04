package com.ydw.game.task;

import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 维护一个多线程类
 * @author xulh
 *
 */
@Component
public class ThreadPool {
	
	private ThreadPoolExecutor pool = new ThreadPoolExecutor(100, 150, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
	
	public void submit(Runnable runnable){
		pool.submit(runnable);
	}
	
	public <V> Future<V> submit(Callable<V> call){
		return pool.submit(call);
	}
}
