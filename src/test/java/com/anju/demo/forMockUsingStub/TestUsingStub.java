package com.anju.demo.forMockUsingStub;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestUsingStub {

	@Test
	void test_methodThantNeedsToBeTested() {
		LogicPart logicPart = new LogicPart();
		UserPreferenceService service = new UserPreferenceServiceStub();
		boolean actual = logicPart.methodThantNeedsToBeTested(service);
		assertTrue(actual);
	}

}
