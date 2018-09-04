package com.bank.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.bank.dao.BankApplicationDao;
import com.bank.exception.BankApplicationException;
import com.bank.model.Customer;
import com.bank.model.Transaction;
import com.bank.model.TransactionType;
import com.bank.service.BankApplicationServiceImpl;

/**
 * Test class for BankApplicationServiceImpl
 * 
 * @author BharatGoel
 *
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class BankApplicationServiceImplTest {

	@InjectMocks
	BankApplicationServiceImpl service;
	
	@Mock
	BankApplicationDao bankDao;
	
	@Mock
	TransactionTemplate template;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		Customer customer = new Customer();
		customer.setName("John");
		customer.setBalance(new BigDecimal("1000"));
		customer.setAccountNumber("000006");
		Mockito.when(template.execute(Mockito.any(TransactionCallback.class))).thenReturn(customer);
	}
	
	@Test
	public void testAddNewCustomer() throws BankApplicationException {
		Customer customer = new Customer();
		customer.setName("John");
		customer.setBalance(new BigDecimal("1000"));
		
		Mockito.when(bankDao.addNewCustomer(Mockito.anyMap(), Mockito.anyString())).thenReturn(true);
		Mockito.when(bankDao.generateAccountID(Mockito.anyMap(), Mockito.anyString())).thenReturn(6);
		
		Customer expectedResult = service.addNewCustomer(customer);
		assertTrue(expectedResult.getAccountNumber().equals("000006"));
	}

	@Test
	public void testGetBalance() throws BankApplicationException {
		BigDecimal expectedResult = new BigDecimal("100");
		Mockito.when(bankDao.getBalance(Mockito.anyMap(), Mockito.anyString())).thenReturn(expectedResult);
		
		BigDecimal actualResult = service.getBalance("100008");
		assertTrue(actualResult.equals(expectedResult));
	}
	
	@Test(expected=BankApplicationException.class)
	public void testGetBalanceException() throws BankApplicationException {
		BigDecimal expectedResult = new BigDecimal("100");
		Mockito.when(bankDao.getBalance(Mockito.anyMap(), Mockito.anyString())).thenReturn(expectedResult);
		
		service.getBalance("1008");
	}
	
	@Test
	public void testExecuteTransactionDeposit() throws BankApplicationException {		
		Transaction transaction = new Transaction();
		transaction.setAccountID("0000008");
		transaction.setAmount(new BigDecimal("100"));
		transaction.setType(TransactionType.DEPOSIT);
		
		Mockito.when(bankDao.getBalance(Mockito.anyMap(), Mockito.anyString())).thenReturn(new BigDecimal("500"));
		Mockito.when(bankDao.doTransaction(Mockito.anyMap(), Mockito.anyString())).thenReturn(true);
		
		BigDecimal actualResult = service.executeTransaction(transaction);
		
		assertTrue(actualResult.equals(new BigDecimal("600")));
	}
	
	@Test
	public void testExecuteTransactionWithdrawalSuccess() throws BankApplicationException {
		Transaction transaction = new Transaction();
		transaction.setAccountID("0000008");
		transaction.setAmount(new BigDecimal("100"));
		transaction.setType(TransactionType.WITHDRAWAL);
		
		Mockito.when(bankDao.getBalance(Mockito.anyMap(), Mockito.anyString())).thenReturn(new BigDecimal("500"));
		Mockito.when(bankDao.doTransaction(Mockito.anyMap(), Mockito.anyString())).thenReturn(true);
		
		BigDecimal actualResult = service.executeTransaction(transaction);
		
		assertTrue(actualResult.equals(new BigDecimal("400")));	
	}
	
	@Test(expected=BankApplicationException.class)
	public void testExecuteTransactionWithdrawalFailure() throws BankApplicationException{
		Transaction transaction = new Transaction();
		transaction.setAccountID("0000008");
		transaction.setAmount(new BigDecimal("1000"));
		transaction.setType(TransactionType.WITHDRAWAL);
		
		Mockito.when(bankDao.getBalance(Mockito.anyMap(), Mockito.anyString())).thenReturn(new BigDecimal("500"));
		
		service.executeTransaction(transaction);
	}
	
	@Test
	public void testFetchAccountID() throws BankApplicationException {
		Mockito.when(bankDao.generateAccountID(Mockito.anyMap(), Mockito.anyString())).thenReturn(5);
		int actualResult = service.fetchAccountID();
		
		assertTrue(actualResult == 5);
	}
	
	@Test
	public void testUpdateAccountID() throws BankApplicationException {	
		Mockito.doNothing().when(bankDao).updateAccountID(Mockito.anyMap(), Mockito.anyString());
		assertTrue(service.updateAccountID(5));
	}
	
	@Test
	public void testAddNewCustomerID() throws BankApplicationException {
		Mockito.when(bankDao.addNewCustomer(Mockito.anyMap(), Mockito.anyString())).thenReturn(true);
		assertTrue(service.addNewCustomerID(new Customer(), 5).equals("000005"));
	}

}
