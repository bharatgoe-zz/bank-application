package com.bank.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bank.dao.BankApplicationDaoImpl;
import com.bank.dao.BankQuery;
import com.bank.exception.BankApplicationException;

/**
 * Test class for BankApplicationDaoImpl
 * 
 * @author BharatGoel
 *
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class BankApplicationDaoImplTest {

	@InjectMocks
	BankApplicationDaoImpl bankDao;
	
	@Mock
	NamedParameterJdbcTemplate jdbcTemplate;
	
	private Map<String, Object> paramMap;
	private String query;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		paramMap = new HashMap<>();
	}
	
	@Test
	public void testAddNewCustomer() throws BankApplicationException {
		query = BankQuery.ADD_CUSTOMER;
		Mockito.when(jdbcTemplate.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(0);
		assertTrue(bankDao.addNewCustomer(paramMap, query));
	}
	
	@Test(expected=BankApplicationException.class)
	public void testAddNewCustomerException() throws BankApplicationException {
		query = BankQuery.ADD_CUSTOMER;
		Mockito.when(jdbcTemplate.update(Mockito.anyString(), Mockito.anyMap())).thenThrow(new DataAccessException("..."){

		private static final long serialVersionUID = -2256685538558167893L; });
		bankDao.addNewCustomer(paramMap, query);
	}

	@Test
	public void testDoTransaction() throws BankApplicationException {
		query = BankQuery.UPDATE_BALANCE;
		Mockito.when(jdbcTemplate.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(0);
		assertTrue(bankDao.doTransaction(paramMap, query));
	}
	
	@Test(expected=BankApplicationException.class)
	public void testDoTransactionException() throws BankApplicationException {
		query = BankQuery.UPDATE_BALANCE;
		Mockito.when(jdbcTemplate.update(Mockito.anyString(), Mockito.anyMap())).thenThrow(new DataAccessException("..."){

		private static final long serialVersionUID = -2656919297896621169L; });
		assertTrue(bankDao.doTransaction(paramMap, query));
	}

	@Test
	public void testGetBalance() throws BankApplicationException {
		query = BankQuery.GET_BALANCE;
		BigDecimal balance = new BigDecimal("1000");
		Mockito.when(jdbcTemplate.queryForObject(query, paramMap, BigDecimal.class)).thenReturn(balance);
		assertTrue(bankDao.getBalance(paramMap, query).equals(balance));
	}
	
	@Test(expected=BankApplicationException.class)
	public void testGetBalanceException() throws BankApplicationException {
		query = BankQuery.GET_BALANCE;
		BigDecimal balance = new BigDecimal("1000");
		Mockito.when(jdbcTemplate.queryForObject(query, paramMap, BigDecimal.class)).thenThrow(new DataAccessException("..."){

		private static final long serialVersionUID = 6535438488719144437L; });
		assertTrue(bankDao.getBalance(paramMap, query).equals(balance));
	}

	@Test
	public void testGenerateAccountID() throws BankApplicationException {
		query = BankQuery.GENERATE_ACC_ID;
		int acc_id = 6;
		Mockito.when(jdbcTemplate.queryForObject(query, paramMap, Integer.class)).thenReturn(acc_id);
		assertTrue(bankDao.generateAccountID(paramMap, query) == acc_id);
	}

	@Test(expected=BankApplicationException.class)
	public void testGenerateAccountIDException() throws BankApplicationException {
		query = BankQuery.GENERATE_ACC_ID;
		int acc_id = 6;
		Mockito.when(jdbcTemplate.queryForObject(query, paramMap, Integer.class)).thenThrow(new DataAccessException("..."){

		private static final long serialVersionUID = 2264314580468127824L; });
		assertTrue(bankDao.generateAccountID(paramMap, query) == acc_id);
	}
	
	@Test
	public void testUpdateAccountID() throws BankApplicationException {
		query = BankQuery.UPDATE_ACC_ID;
		Mockito.when(jdbcTemplate.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(0);
		bankDao.updateAccountID(paramMap, query);
	}
	
	@Test(expected=BankApplicationException.class)
	public void testUpdateAccountIDException() throws BankApplicationException {
		query = BankQuery.UPDATE_ACC_ID;
		Mockito.when(jdbcTemplate.update(Mockito.anyString(), Mockito.anyMap())).thenThrow(new DataAccessException("..."){

		private static final long serialVersionUID = -8574004881422237814L; });
		bankDao.updateAccountID(paramMap, query);
	}

}
