package com.anju.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.anju.demo.controller.RabbitRestController;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

@SpringBootTest
class RabbitMokitoRefreshApplicationTests {

	@Test
	void test1() {
		RabbitRestController r = new RabbitRestController();
		//assertThrows(ArrayIndexOutOfBoundsException.class, ()->r.testExceptionJUnit());
		assertTimeout(Duration.ofMillis(100), () -> r.testExceptionJUnit());
	}
	
	@Test
	void test2() {
		assertEquals(true, true);
	}

}
