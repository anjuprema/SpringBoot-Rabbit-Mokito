package com.anju.demo.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopicExchange {
	
	@Bean
	public Queue getTopicQueueOne() {
		return new Queue("topicQueueOne",false);
	}
	
	@Bean
	public Queue getTopicQueueTwo() {
		return new Queue("topicQueueTwo",false);
	}
	
	@Bean
	public Queue getTopicQueueAll() {
		return new Queue("topicQueueAll",false);
	}
	
	@Bean
	public TopicExchange getTopicExchange() {
		return new TopicExchange("topic-exchange");
	}
	
	@Bean
	public Binding getBindingForQueueOne(Queue getTopicQueueOne, TopicExchange getTopicExchange) {
		return BindingBuilder.bind(getTopicQueueOne).to(getTopicExchange).with("routingKeyForTopicQueue.One");
	}
	
	@Bean
	public Binding getBindingForQueueTwo(Queue getTopicQueueTwo, TopicExchange getTopicExchange) {
		return BindingBuilder.bind(getTopicQueueTwo).to(getTopicExchange).with("routingKeyForTopicQueue.Two");
	}
	
	@Bean
	public Binding getBindingForQueueAll(Queue getTopicQueueAll, TopicExchange getTopicExchange) {
		return BindingBuilder.bind(getTopicQueueAll).to(getTopicExchange).with("routingKeyForTopicQueue.*");
	}
}
