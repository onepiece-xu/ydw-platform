package com.ydw.game.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ydw.game.model.enums.MessageTopicEnum;

@Configuration
public class RedisConfig {

	@Bean
	@SuppressWarnings("all")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
		template.setConnectionFactory(factory);
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		// key采用String的序列化方式
		template.setKeySerializer(stringRedisSerializer);
		// hash的key也采用String的序列化方式
		template.setHashKeySerializer(stringRedisSerializer);
		// value序列化方式采用jackson
		template.setValueSerializer(jackson2JsonRedisSerializer);
		// hash的value序列化方式采用jackson
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public ConnectMessageSub instanceMessageSub() {
		return new ConnectMessageSub();
	}

//	@Bean
//	public CommentMessageSub commentMessageSub() {
//		return new CommentMessageSub();
//	}
//
//	@Bean
//	public NoticeMessageSub noticeMessageSub() {
//		return new NoticeMessageSub();
//	}
//
//	@Bean
//	public IdeaMessageSub ideaMessageSub() {
//		return new IdeaMessageSub();
//	}

	/**
	 * 消息侦听器容器
	 * @author xulh
	 * @date 2019年8月13日
	 * @param connectionFactory
	 * @param listenerAdapter
	 * @return RedisMessageListenerContainer
	 */
	@Bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(instancelistenerAdapter(instanceMessageSub()),
				new PatternTopic(MessageTopicEnum.CONNECT.getTopic()));
//		container.addMessageListener(listenerAdapter(noticeMessageSub()),
//				new PatternTopic(ChannelEnum.NOTICE.getChannel()));
//		container.addMessageListener(listenerAdapter(ideaMessageSub()),
//				new PatternTopic(ChannelEnum.IDEA.getChannel()));
		return container;
	}

	/**
	 * 消息侦听器适配器
	 * 
	 * @author xulh
	 * @date 2019年8月13日
	 * @param receiver
	 * @return MessageListenerAdapter
	 */
	@Bean
	public MessageListenerAdapter instancelistenerAdapter(IRedisMessageSub receiver) {
		MessageListenerAdapter messageListenerAdapter = new MessageListenerAdapter(receiver, "receiveMessage");
		return messageListenerAdapter;
	}
}