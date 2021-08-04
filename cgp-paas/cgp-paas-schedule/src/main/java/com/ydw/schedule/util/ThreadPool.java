package com.ydw.schedule.util;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 维护一个多线程类
 * @author xulh
 *
 */
@Component
public class ThreadPool {
	
	@Value("${threadpool.coreNum}")
	private Integer coreNum;
	
	@Value("${threadpool.maxNum}")
	private Integer maxNum;
	
	private volatile ThreadPoolExecutor pool;
	
	public void init(){
		if(pool == null){
			synchronized (this) {
				if(pool == null){
					pool = new ThreadPoolExecutor(coreNum, maxNum, 5, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
				}
			}
		}
		
	}
	
	public void submit(Runnable runnable){
		init();
		pool.submit(runnable);
	}
	
	public void execute(Runnable runnable){
		init();
	    pool.execute(runnable);
	}
	
	public <V> Future<V> submit(Callable<V> call){
		init();
		return pool.submit(call);
	}
}
