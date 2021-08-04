package com.ydw.game.redis;

/**
 * redis消息处理接口，redis只做消息转发，不做其他处理
 * @author xulh
 *
 */
public interface IRedisMessageSub {

	public void receiveMessage(String message);
}
