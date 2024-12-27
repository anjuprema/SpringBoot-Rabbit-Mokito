package com.anju.demo.forMockUsingMokito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.anju.demo.forMockUsingStub.LogicPart;
import com.anju.demo.forMockUsingStub.UserPreferenceService;

class TestUsingMokito {

	@Test
	void test_methodThantNeedsToBeTested() {
		LogicPart logicPart = new LogicPart();
		UserPreferenceService service = mock(UserPreferenceService.class);
		when(service.getUserPreferredFruits(12)).thenReturn("Mango");
		boolean actual = logicPart.methodThantNeedsToBeTested(service);
		assertTrue(actual);
	}

}
