package com.anju.demo.service;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQService {
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	public void sendMessage(String exchange, String routingKey, String message) {
		rabbitTemplate.convertAndSend(exchange, routingKey, message);
	}
}
