package com.anju.demo.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.HeadersExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQHeaderExchangeConfig {
	
	@Bean
	public Queue getHeaderQueueOne() {
		return new Queue("headerQueueOne",false);
	}
	
	@Bean
	public Queue getHeaderQueueTwo() {
		return new Queue("headerQueueTwo",false);
	}
	
	@Bean
	public Queue getHeaderQueueThree() {
		return new Queue("headerQueueThree",false);
	}
	
	@Bean
	public HeadersExchange getHeaderExchange() {
		return new HeadersExchange("header-exchange");
	}
	
	@Bean
	public Binding getHeaderBindingForQueueOne(Queue getHeaderQueueOne, HeadersExchange getHeaderExchange) {
		return BindingBuilder.bind(getHeaderQueueOne).to(getHeaderExchange).where("group").matches("group1");
	}
	
	@Bean
	public Binding getHeaderBindingForQueueTwo(Queue getHeaderQueueTwo, HeadersExchange getHeaderExchange) {
		return BindingBuilder.bind(getHeaderQueueTwo).to(getHeaderExchange).where("group").matches("group2");
	}
	
	@Bean
	public Binding getHeaderBindingForQueueThree(Queue getHeaderQueueThree, HeadersExchange getHeaderExchange) {
		return BindingBuilder.bind(getHeaderQueueThree).to(getHeaderExchange).where("group").matches("group2");
	}
}
