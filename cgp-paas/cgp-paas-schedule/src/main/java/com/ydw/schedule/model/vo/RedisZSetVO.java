package com.ydw.schedule.model.vo;

import org.springframework.data.redis.core.ZSetOperations.TypedTuple;

public class RedisZSetVO<V> implements TypedTuple<V>{
	
	private V value;
	
	private Double score; 
	
	public RedisZSetVO() {
		super();
	}
	
	public RedisZSetVO(V value, Double score) {
		super();
		this.value = value;
		this.score = score;
	}

	@Override
	public Double getScore() {
		return this.score;
	}

	@Override
	public int compareTo(TypedTuple<V> o) {
		return this.score.compareTo(o.getScore());
	}

	@Override
	public V getValue() {
		return value;
	}

}
