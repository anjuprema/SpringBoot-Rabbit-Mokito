package com.anju.demo.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQFanoutConfig {
	
	@Bean
	public Queue getFanoutQueueOne() {
		return new Queue("fanoutQueueOne",false);
	}
	
	@Bean
	public Queue getFanoutQueueTwo() {
		return new Queue("fanoutQueueTwo",false);
	}
	
	@Bean
	public Queue getFanoutQueueThree() {
		return new Queue("fanoutQueueThree",false);
	}
	
	@Bean
	public FanoutExchange getFanoutExchange() {
		return new FanoutExchange("fanout-exchange");
	}

	@Bean
	public Binding getBindingOne(Queue getFanoutQueueOne, FanoutExchange getFanoutExchange) {
		return BindingBuilder.bind(getFanoutQueueOne).to(getFanoutExchange);
	}
	
	@Bean
	public Binding getBindingTwo(Queue getFanoutQueueTwo, FanoutExchange getFanoutExchange) {
		return BindingBuilder.bind(getFanoutQueueTwo).to(getFanoutExchange);
	}
	
	@Bean
	public Binding getBindingThree(Queue getFanoutQueueThree, FanoutExchange getFanoutExchange) {
		return BindingBuilder.bind(getFanoutQueueThree).to(getFanoutExchange);
	}
}
