package com.bank.dao;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.bank.exception.BankApplicationException;
import com.bank.util.BankApplicationConstants;

/**
 * Implementation of Data Access Object Layer
 * for Bank Application.
 * 
 * @author BharatGoel
 *
 */
@Component("postGresImpl")
public class BankApplicationDaoImpl implements BankApplicationDao {
	
	@Autowired
	@Qualifier("bankJdbcTemplate")
	NamedParameterJdbcTemplate jdbcTemplate;
	
	@Override
	public boolean addNewCustomer(Map<String, Object> paramMap, String query) throws BankApplicationException {
		try {
			jdbcTemplate.update(query, paramMap);
		} catch (DataAccessException e) {
			throw new BankApplicationException(
					"Unable to create account for customer " + paramMap.get(BankApplicationConstants.CUSTOMER_NAME));
		}
		return true;
	}

	@Override
	public boolean doTransaction(Map<String, Object> paramMap, String query) throws BankApplicationException {
		try {
			jdbcTemplate.update(query, paramMap);
			return true;
		} catch (DataAccessException e) {
			throw new BankApplicationException(
					"Unable to update bank balance for account ID " + paramMap.get(BankApplicationConstants.ACCOUNT_ID));
		}
	}

	@Override
	public BigDecimal getBalance(Map<String, Object> paramMap, String query) throws BankApplicationException {
		try {
			return jdbcTemplate.queryForObject(query, paramMap, BigDecimal.class);
		} catch (DataAccessException e) {
			throw new BankApplicationException(
					"Unable to get balance details for account ID " + paramMap.get(BankApplicationConstants.ACCOUNT_ID));
		}
	}

	@Override
	public int generateAccountID(Map<String, Object> paramMap, String query) throws BankApplicationException {
		int accountID = 0;
		try {
			accountID = jdbcTemplate.queryForObject(query, paramMap, Integer.class);
		} catch (DataAccessException e) {
			throw new BankApplicationException("Unable to generate new accound ID");
		}
		return accountID;
	}

	@Override
	public void updateAccountID(Map<String, Object> paramMap, String query) throws BankApplicationException {
		try {
			jdbcTemplate.update(query, paramMap);
		} catch (DataAccessException e) {
			throw new BankApplicationException(
					"Unable to update new account ID " + paramMap.get(BankApplicationConstants.ACCOUNT_ID));
		}
	}
	
}
