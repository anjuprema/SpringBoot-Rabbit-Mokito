package com.anju.demo.controller;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anju.demo.component.Employee;
import com.anju.demo.service.RabbitMQService;
import com.anju.demo.service.UtilityClass;

@RestController
public class RabbitRestController {
	@Autowired
	RabbitMQService rabbitService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${application.written.by}")
	private String applicationWrittenBy;
	
	@Value("${dateOfBirth}")
	private String dob; // Relaxed Binding, Can use camelCase/ kabab-case/ snake_case /UPPER_CASE
	
	@Autowired 
	Environment env; // For accessing environment variables
	
	private static final Logger log = LoggerFactory.getLogger(RabbitRestController.class) ;
	
	@GetMapping("/")
	public String printAppDetails() {		
		System.setProperty("IsEnvironmentSet", "true");
		return "Application written by: "+ applicationWrittenBy
				+"<br>Date Of Birth: "+dob
				+"<br>Is Environmet Variables set: "+env.getProperty("IsEnvironmentSet");
	}
	
	@GetMapping("sendMessage/directExchange")
	public void sendDirectMessageToRabbit(@RequestParam("myMessage") String message) {
		rabbitService.sendMessage("direct-exchange", "routingkeyForQueueOne", message);
		rabbitService.sendMessage("direct-exchange", "routingkeyForQueueTwo", "Message send to Queue One");
	}
	
	@GetMapping("sendMessage/fanoutExchange")
	public void sendFanoutMessageToRabbit(@RequestParam("myMessage") String message) {
		rabbitService.sendMessage("fanout-exchange","", message);
	}
	
	@GetMapping("sendMessage/topicExchange")
	public void sendTopicMessageToRabbit(@RequestParam("myMessage") String message) {
		rabbitService.sendMessage("topic-exchange","routingKeyForTopicQueue.One", message);
	}
	
	@GetMapping("sendMessage/headerExchange")
	public void sendHeaderMessageToRabbit(@RequestParam("myMessage") String messageStr, @RequestParam("group") String group) {
		MessageProperties property = new MessageProperties();
		property.setHeader("group", group);
		
		MessageConverter mc = new SimpleMessageConverter();
		Message message = mc.toMessage(messageStr, property);
		
		rabbitTemplate.send("header-exchange","", message);
	}
	
	public void testExceptionJUnit() {
		//throw new NullPointerException();
		int[] arr = new int[] {1,3,5,2,3};
		for(int i=0; i<1000000; i++) {
			Arrays.sort(arr);
		}
	}
	
	private void testPrivateMethodUsingReflectionAPINoParam() {
		//Refer RabbitRestControllerTest.java -> testPrivateMethodUsingReflectionAPINoParam()
		System.out.println("testPrivateMethodUsingReflectionAPINoParam invoked..");
		for(int i=0; i<10; i++) {
			System.out.println(i);
		}
	}
	
	private void testPrivateMethodUsingReflectionAPIWithParam(Integer val, String str) {
		//Refer RabbitRestControllerTest.java -> testPrivateMethodUsingReflectionAPIWithParam()
		System.out.println("testPrivateMethodUsingReflectionAPIWithParam invoked..");
		for(int i=0; i<val; i++) {
			System.out.println("i: "+ i + ", val: "+ val + ", string: "+ str);
		}
	}
	
	private boolean testPrivateMethodUsingReflectionAPIWithParamAndReturn(Integer val, String str) {
		System.out.println("testPrivateMethodUsingReflectionAPIWithParamAndReturn invoked..");
		//Refer RabbitRestControllerTest.java -> testPrivateMethodUsingReflectionAPIWithParamAndReturn()
		for(int i=0; i<val; i++) {
			System.out.println("i: "+ i + ", val: "+ val + ", string: "+ str +", will return false");
		}
		return false;
	}
	
	public int testpublicMethodwithStaticMethodCallUsingMokito(Integer a, Integer b) {
		//refer RabbitRestControllerTest.java -> testpublicMethodwithStaticMethodCallUsingMokito()
		return UtilityClass.getSum(a, b);
	}
	
	@GetMapping("/testRequestParam")
	public String methodRequestParam(@RequestParam("yourName") String name) {
		//Refer RabbitRestControllerTest.java -> testRequestParam()
		return name;
	}
	
	@PostMapping("/testRequestBody")
	public Employee methodRequestBody(@RequestBody Employee emp) {
		//Refer RabbitRestControllerTest.java -> testRequestBody()
		return emp;
		//return new Employee(emp.getEmpId(),emp.getEmpName()); //this will fail as new object is created
	}
	
	public static int testStaticMethod(Integer a, Integer b) {
		return a*b;
	}
	
	@GetMapping("/testLogger")
	public void testLogger(@RequestParam("name") String name) {
		//basic logging
		log.debug("This is a bebug message");
		log.error("This is an error message");
		log.info("This is an info log");
		log.trace("This is a trace log");
		log.warn("This is a warning message");
		
		//logging with a format
		String msg = "ISSUE HAPPENED..";
		log.debug("This is a formatted bebug message: {}", msg);
		log.error("This is a formatted error message: {}", msg);
		log.info("This is a formatted info log: {}", msg);
		log.trace("This is a formatted trace log: {}", msg);
		log.warn("This is a formatted warning message: {}", msg);
		
		if(name.equals("anju")) throw new RuntimeException("Oops name passed is Anju..");
	}

}
