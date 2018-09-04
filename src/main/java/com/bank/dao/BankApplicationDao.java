package com.bank.dao;

import java.math.BigDecimal;
import java.util.Map;

import com.bank.exception.BankApplicationException;

/**
 * This class represents the data access object layer 
 * for the Bank application.
 * 
 * @author BharatGoel
 *
 */
public interface BankApplicationDao {
	
	/**
	 * Adds new customer in the database.
	 * 
	 * @param paramMap
	 * @return true if operation was success, false otherwise
	 */
	public boolean addNewCustomer(Map<String, Object> paramMap, String query) throws BankApplicationException;
	
	/**
	 * Executes a transaction, given the transaction details.
	 * Updates the database with the updated balance sent from
	 * service layer.
	 * 
	 * @param paramMap
	 * @return true if operation was success, false otherwise
	 */
	public boolean doTransaction(Map<String, Object> paramMap, String query) throws BankApplicationException;
	
	/**
	 * Returns the account balance, given the accountID.
	 * 
	 * @param accountID
	 * @return balance
	 */
	public BigDecimal getBalance(Map<String, Object> paramMap, String query) throws BankApplicationException ;	

	/**
	 * Generates and returns the next available account ID.
	 * 
	 * @param paramMap
	 * @param query
	 * @return account ID
	 * @throws BankApplicationException
	 */
	public int generateAccountID(Map<String, Object> paramMap, String query) throws BankApplicationException;
	
	/**
	 * Updates the new generated account ID in the database.
	 * 
	 * @param paramMap
	 * @param query
	 * @throws BankApplicationException
	 */
	void updateAccountID(Map<String, Object> paramMap, String query) throws BankApplicationException;

}
