package com.bank.controller;

import java.io.IOException;
import java.math.BigDecimal;

import org.beanio.BeanIOConfigurationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bank.application.Application;
import com.bank.controller.BankApplicationController;
import com.bank.exception.BankApplicationException;
import com.bank.model.Customer;
import com.bank.model.Transaction;
import com.bank.service.BankApplicationService;

import junit.framework.TestCase;

/**
 * Test class for BankApplicationController
 * 
 * @author BharatGoel
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value=BankApplicationController.class, secure=false)
@ContextConfiguration(classes={Application.class})
public class BankApplicationControllerTest extends TestCase {

	@InjectMocks
	BankApplicationController controller;
	
	@MockBean
	BankApplicationService service;
	
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;
	
	@Before
	public void setUp() throws BeanIOConfigurationException, IOException, Exception {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void testAddCustomer() throws Exception {
		String inputJson = "{ \"name\": \"John\", \"balance\": \"100\"}";
		Customer response = new Customer();
		response.setAccountNumber("100001");
		response.setName("John");
		response.setBalance(new BigDecimal("100"));
		
		Mockito.when(service.addNewCustomer(Mockito.any(Customer.class))).thenReturn(response);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addcustomer")
										.accept(MediaType.APPLICATION_JSON)
										.content(inputJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	
		assertTrue(result.getResponse().getContentAsString().equals("New customer: John added, with account number: 100001"));
		assertEquals(result.getResponse().getStatus(), 200);
	}
	
	@Test
	public void testAddCustomerFailure() throws Exception {
		String inputJson = "{ \"name\": \"John\", \"balance\": \"100\"}";
		Customer response = new Customer();
		
		Mockito.when(service.addNewCustomer(Mockito.any(Customer.class))).thenReturn(response);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addcustomer")
										.accept(MediaType.APPLICATION_JSON)
										.content(inputJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertTrue(result.getResponse().getContentAsString().equals("Unable to add new customer John"));
		assertEquals(result.getResponse().getStatus(), 500);
	}
	
	@Test
	public void testAddCustomerException() throws Exception {
		String inputJson = "{ \"name\": \"John\", \"balance\": \"100\"}";
		
		Mockito.when(service.addNewCustomer(Mockito.any(Customer.class))).thenThrow(new BankApplicationException("Exception"));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/addcustomer")
										.accept(MediaType.APPLICATION_JSON)
										.content(inputJson)
										.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertTrue(result.getResponse().getContentAsString().equals("Exception"));
		assertEquals(result.getResponse().getStatus(), 500);
	}

	@Test
	public void testCheckBalance() throws Exception {
		String inputJson = "000007";
		
		Mockito.when(service.getBalance(Mockito.anyString())).thenReturn(new BigDecimal("1000"));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/checkbalance")
				.accept(MediaType.APPLICATION_JSON)
				.content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(result.getResponse().getContentAsString(), "1000");
		assertEquals(result.getResponse().getStatus(), 200);
	}
	
	@Test
	public void testCheckBalanceException() throws Exception {
		String inputJson = "000007";
		
		Mockito.when(service.getBalance(Mockito.anyString())).thenThrow(new BankApplicationException("Exception"));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/checkbalance")
				.accept(MediaType.APPLICATION_JSON)
				.content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		assertEquals(result.getResponse().getContentAsString(), "Exception");
		assertEquals(result.getResponse().getStatus(), 500);
	}

	@Test
	public void testDoTransaction() throws Exception {
		String inputJson = "{\"accountID\" : \"7878787\",\r\n" + 
				" \"amount\" : \"1000\",\r\n" + 
				" \"type\" : \"DEPOSIT\"\r\n" + 
				"}";
		Mockito.when(service.executeTransaction(Mockito.any(Transaction.class))).thenReturn(new BigDecimal("1000"));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/dotransaction")
				.accept(MediaType.APPLICATION_JSON)
				.content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(result.getResponse().getContentAsString(), "1000");
		assertEquals(result.getResponse().getStatus(), 200);
	}
	
	@Test
	public void testDoTransactionException() throws Exception {
		String inputJson = "{\"accountID\" : \"7878787\",\r\n" + 
				" \"amount\" : \"1000\",\r\n" + 
				" \"type\" : \"DEPOSIT\"\r\n" + 
				"}";
		Mockito.when(service.executeTransaction(Mockito.any(Transaction.class))).thenThrow(new BankApplicationException("Exception"));
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/dotransaction")
				.accept(MediaType.APPLICATION_JSON)
				.content(inputJson)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(result.getResponse().getContentAsString(), "Exception");
		assertEquals(result.getResponse().getStatus(), 500);
	}


}
