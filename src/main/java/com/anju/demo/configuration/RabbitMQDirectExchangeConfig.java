package com.anju.demo.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQDirectExchangeConfig {
	
	@Bean
	Queue getQueueOne() {
		return new Queue("queueOne", false);
	}
	
	@Bean
	Queue getQueueTwo () {
		return new Queue("queueTwo", false);
	}
	
	@Bean
	DirectExchange getDirectExchange() {
		return new DirectExchange("direct-exchange");
	}
	
	@Bean
	Binding getQueueOneBinding(Queue getQueueOne, DirectExchange ex) {
		return BindingBuilder.bind(getQueueOne).to(ex).with("routingkeyForQueueOne");
	}
	
	@Bean
	Binding getQueueTwoBinding(Queue getQueueTwo, DirectExchange ex) {
		return BindingBuilder.bind(getQueueTwo).to(ex).with("routingkeyForQueueTwo");
	}

}
