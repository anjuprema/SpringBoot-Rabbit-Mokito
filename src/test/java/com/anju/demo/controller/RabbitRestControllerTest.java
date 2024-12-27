package com.anju.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.*;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.anju.demo.component.Employee;
import com.anju.demo.service.RabbitMQService;

//@RunWith(MockitoJUnitRunner.class)
class RabbitRestControllerTest {
	//@InjectMocks
	public static RabbitRestController rabbitRestController;
	
	//@Mock
	RabbitMQService rabbitService;
	
	@Spy
	private ArrayList<String> al = spy(new ArrayList());
	
	@Mock
	private ArrayList<String> alMock = mock(ArrayList.class);
	
	@BeforeAll
	public static void init() {
		rabbitRestController = new RabbitRestController();	
	}
	
	
	@Test
	public void sendDirectMessageToRabbitTest() {
		RabbitMQService rs = mock(RabbitMQService.class);
		String message = "Test Direct Message Using JUnit";
		rs.sendMessage("direct-exchange", "routingkeyForQueueOne", message); // but how to call and verify rabbitRestController.sendDirectMessageToRabbit() ??
																			 //see sendDirectMessageToRabbitServiceTest()
		verify(rs, times(1)).sendMessage("direct-exchange", "routingkeyForQueueOne", message);		
	}
	
	@Test
	public void sendDirectMessageToRabbitServiceTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		String message = "Test Direct Message Using JUnit";
		//create mock of rabbitService and provide to rabbitRestController
		//call rabbit controller and see rabbit service is called once
		
		//creating mock rabbitservice
		rabbitService = mock(RabbitMQService.class);
		
		//setting rabbitService of rabbitRestController
		Field rs = rabbitRestController.getClass().getDeclaredField("rabbitService");
		rs.setAccessible(true);
		rs.set(rabbitRestController, rabbitService);
		
		//invoking rabbitRestController
		rabbitRestController.sendDirectMessageToRabbit(message);
		
		//verifying if rabbitService is called from rabbitRestController
		verify(rabbitService, times(1)).sendMessage("direct-exchange", "routingkeyForQueueOne", message);
	}
	
	@Test
	public void noCallsToMethodTest() {
		RabbitMQService r1 = mock(RabbitMQService.class);
		RabbitMQService r2 = mock(RabbitMQService.class);
		
		//r1.sendMessage("direct-exchange", "routingkeyForQueueOne", "Hello World!!");  <-- uncoment and see
		verifyNoMoreInteractions(r1, r2);
	}
	
//	@Test
//	public void sendDirectMessageToRabbitServiceTestUsingMokito() {
//		String message = "Test Direct Message Using JUnit";
//		doNothing().when(rabbitService).sendMessage("direct-exchange", "routingkeyForQueueOne", message);
//		rabbitRestController.sendDirectMessageToRabbit(message);
//		verify(rabbitService, times(1)).sendMessage("direct-exchange", "routingkeyForQueueOne", message);
//	}
	
	@Test
	public void mockListSizeMethodTest() {
		List mokedList = mock(List.class);    // mock list
		when(mokedList.size()).thenReturn(2); //when mokedList.size() method is called return 2
		
		assertEquals(2, mokedList.size());
	}
	
	@Test
	public void mockListSizeMethodMultipleTest() {
		List mokedList = mock(List.class);    // mock list
		when(mokedList.size()).thenReturn(2).thenReturn(5); //when mokedList.size() method is called return 2 first time 
															// and 2nd time it will return 5		
		assertEquals(2, mokedList.size());
		assertEquals(5, mokedList.size());
	}
	
	@Test
	public void mockListGetMethodTest() {
		List mokedList = mock(List.class);    // mock list
		when(mokedList.get(0)).thenReturn("Data at position zero"); //when mokedList.get(position) method is called return a string
		
		assertEquals("Data at position zero", mokedList.get(0));
		assertEquals(null, mokedList.get(1));  //not mocked, so will return null, and TC will pass
	}
	
	@Test
	public void mockListGetUsingArgumentMatcherTest() {
		List mokedList = mock(List.class);    // mock list
		//Argument Matcher - anyInt()
		when(mokedList.get(anyInt())).thenReturn("Data"); //when mokedList.get(for any integer) is called return a string 'Data'
		when(mokedList.get(99999)).thenThrow(new ArithmeticException()); // for a particular value throw an exception
		
		assertEquals("Data", mokedList.get(0));
		assertEquals("Data", mokedList.get(100));
		assertEquals("Data", mokedList.get(2000));
		assertEquals("Data", mokedList.get(-20));
		assertEquals("Data", mokedList.get(-3000));
		assertThrows(ArithmeticException.class, () -> mokedList.get(99999)); // OR @Test(expected=ArithmeticException.class) && mokedList.get(99999);
	}
	
	//Test private method using Reflection API of Java (use power mock instead)
	@Test
	public void testPrivateMethodUsingReflectionAPINoParam() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//Create class, find the method, make it accessible and invoke it..
		//create class
		Class controllerClass = Class.forName("com.anju.demo.controller.RabbitRestController");
		RabbitRestController rabbitRestController = (RabbitRestController) controllerClass.newInstance();
		
		//find the method, pass null as no params
		Method testPrivateMethodUsingReflectionAPINoParam = controllerClass.getDeclaredMethod("testPrivateMethodUsingReflectionAPINoParam", null);
		
		//make it accessible
		testPrivateMethodUsingReflectionAPINoParam.setAccessible(true);
		
		//invoke it, pass null as no params (See the result in console)
		testPrivateMethodUsingReflectionAPINoParam.invoke(rabbitRestController, null);
	}
	
	@Test
	public void testPrivateMethodNoParamUsingPowerMock() throws Exception {
		//testPrivateMethodUsingReflectionAPINoParam using power mock
		Whitebox.invokeMethod(rabbitRestController, "testPrivateMethodUsingReflectionAPINoParam"); //null not required for param
	}
	
	//test private method with param using Reflection API
	@Test
	public void testPrivateMethodUsingReflectionAPIWithParam() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//Create class, find the method, make it accessible and invoke it..
		//create the class
		Class controllerClass = Class.forName("com.anju.demo.controller.RabbitRestController");
		RabbitRestController rabbitRestController = (RabbitRestController) controllerClass.newInstance();
		
		//find the method, provide param types, if multiple seperate by comma
		Method testPrivateMethodUsingReflectionAPIWithParam = controllerClass.getDeclaredMethod("testPrivateMethodUsingReflectionAPIWithParam", Integer.class, String.class);
		
		//make it accessible
		testPrivateMethodUsingReflectionAPIWithParam.setAccessible(true);
		
		//invoke it, pass params. (See the result in console)
		testPrivateMethodUsingReflectionAPIWithParam.invoke(rabbitRestController, 5, "Anju");
	}
	
	@Test
	public void testPrivateMethodWithParamUsingPowerMock() throws Exception {
		//testPrivateMethodUsingReflectionAPIWithParam using power mock
		Whitebox.invokeMethod(rabbitRestController, "testPrivateMethodUsingReflectionAPIWithParam", 8, "APPU");
	}
	
	//test private method with param and return using Reflection API
	@Test
	public void testPrivateMethodUsingReflectionAPIWithParamAndReturn() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//Create class, find the method, make it accessible and invoke it..
		//create the class
		Class controllerClass = Class.forName("com.anju.demo.controller.RabbitRestController");
		RabbitRestController rabbitRestController = (RabbitRestController) controllerClass.newInstance();
		
		//find the method, provide param types, if multiple seperate by comma
		Method testPrivateMethodUsingReflectionAPIWithParamAndReturn = controllerClass.getDeclaredMethod("testPrivateMethodUsingReflectionAPIWithParamAndReturn", Integer.class, String.class);
		
		//make it accessible
		testPrivateMethodUsingReflectionAPIWithParamAndReturn.setAccessible(true);
		
		//invoke it, pass params. (See the result in console)
		boolean result = (boolean) testPrivateMethodUsingReflectionAPIWithParamAndReturn.invoke(rabbitRestController, 5, "Anju");
		assertEquals(false, result);
	}
	
	@Test
	public void testPrivateMethodWithParamAndReturnUsingPowerMock() throws Exception {
		boolean returns = Whitebox.invokeMethod(rabbitRestController, "testPrivateMethodUsingReflectionAPIWithParamAndReturn", 6, "PARU");
		System.out.println("Got result: "+returns);
		assertEquals(false, returns);
	}
	
	@Test
	public void testpublicMethodwithStaticMethodCallUsingMokito() {
		int result = rabbitRestController.testpublicMethodwithStaticMethodCallUsingMokito(5, 6);
		assertEquals(11, result);
	}
	
	@Test
	public void testSpy() {
		//al is a spy list private variable
		al.add("test");
		//when size() & get() are called, actual methods of list are called when it is a spy
		//but if it is a mock you need to specify what values needs to be returned
		
		//verify if add("test") is called on al
		verify(al).add("test");
		assertEquals(1, al.size());
		assertEquals("test", al.get(0));
	}
	
	@Test
	public void testMock() {
		alMock.add("Anju");
		
		//needs to specify what needs to be returned as alMock is a mock
		//if it was spy, actual size() & get() methods are called 
		when(alMock.size()).thenReturn(1);        //<--- comment and see (not required in testSpy())
		when(alMock.get(0)).thenReturn("Anju");   //<--- comment and see (not required in testSpy())
		
		verify(alMock).add("Anju");
		assertEquals(1, alMock.size());
		assertEquals("Anju", alMock.get(0));
	}
	
	@Test
	@Order(2)
	public void testRequestParam() {
		assertEquals("Anju", rabbitRestController.methodRequestParam("Anju"));
	}
	
	@Test
	@Order(1)
	public void testRequestBody() {
		Employee e = new Employee(1, "Anju");
		assertEquals(e, rabbitRestController.methodRequestBody(e));
		System.out.println(e);
		System.out.println(rabbitRestController.methodRequestBody(e));
	}

}
