package com.bank.service;

import java.math.BigDecimal;

import com.bank.exception.BankApplicationException;
import com.bank.model.Customer;
import com.bank.model.Transaction;

/**
 * This class represents the Service Layer for the Bank Application.
 * 
 * @author BharatGoel
 *
 */
public interface BankApplicationService {
	
	/**
	 * Add new customer to the bank.
	 * 
	 * @param customer
	 * @return boolean - true if operation was successful, false otherwise.
	 * @throws BankApplicationException 
	 */
	public Customer addNewCustomer(Customer customer) throws BankApplicationException;
	
	/**
	 * Returns the balance, give the accountID.
	 *
	 * @param accountID
	 * @return Balance
	 * @throws BankApplicationException 
	 */
	public BigDecimal getBalance(String accountID) throws BankApplicationException;
	
	/**
	 * Executes the given transaction, and returns
	 * the final balance in the customer's account.
	 * 
	 * @param Transaction
	 * @return Balance
	 * @throws BankApplicationException 
	 */
	public BigDecimal executeTransaction(Transaction transaction) throws BankApplicationException;
	

}
