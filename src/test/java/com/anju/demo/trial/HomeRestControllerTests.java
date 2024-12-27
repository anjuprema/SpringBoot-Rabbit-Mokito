package com.anju.demo.trial;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;

@SpringBootTest
class HomeRestControllerTests {

	@Test
	void test1() {
		assertEquals(5,5);
	}
	
	@Test
	void test2() {
		assertEquals(true, true);
	}

}
