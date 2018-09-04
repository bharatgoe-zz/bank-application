package com.bank.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import com.bank.dao.BankApplicationDao;
import com.bank.dao.BankQuery;
import com.bank.exception.BankApplicationException;
import com.bank.model.Customer;
import com.bank.model.Transaction;
import com.bank.model.TransactionType;
import com.bank.util.BankApplicationConstants;
import com.bank.util.BankApplicationUtils;

/**
 * Implementation of Service layer
 * for Bank Application.
 * 
 * @author BharatGoel
 *
 */
@Component
public class BankApplicationServiceImpl implements BankApplicationService {

	@Autowired
	@Qualifier("postGresImpl")
	BankApplicationDao bankDao;
	
	@Autowired
	@Qualifier("bankTemplate")
	TransactionTemplate template;
	
	@Override
	public synchronized Customer addNewCustomer(Customer customer) throws BankApplicationException {
		return template.execute(status -> {
			try {
				int accountID = fetchAccountID();
				String newAccountID = addNewCustomerID(customer, accountID);
				updateAccountID(accountID);
				customer.setAccountNumber(newAccountID);
				return customer;
			} catch (BankApplicationException e) {
				status.setRollbackOnly();
				return customer;
			}
		});
	}
	
	/**
	 * Returns newly generated account ID.
	 * Queries the DAO layer for next available account ID
	 * and returns it.
	 * 
	 * @return accountID
	 * @throws BankApplicationException
	 */
	protected int fetchAccountID() throws BankApplicationException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(BankApplicationConstants.PARAM_KEY, BankApplicationConstants.BANK_KEY);
		return bankDao.generateAccountID(paramMap, BankQuery.GENERATE_ACC_ID);
	}
	
	/**
	 * Updates the updated account ID in the DAO layer.
	 * 
	 * @param accountID
	 * @throws BankApplicationException
	 */
	protected boolean updateAccountID(int accountID) throws BankApplicationException{
		Map<String, Object> paramMap = new HashMap<>();
		int newAccountID = accountID + 1;
		paramMap.put(BankApplicationConstants.PARAM_KEY, BankApplicationConstants.BANK_KEY);
		paramMap.put(BankApplicationConstants.ACCOUNT_ID, newAccountID);
		bankDao.updateAccountID(paramMap, BankQuery.UPDATE_ACC_ID);
		
		return true;
	}

	/**
	 * Prepares accountID to a 6-digit account number.
	 * Creates a new customer in DAO layer.
	 * 
	 * @param customer
	 * @param accountID
	 * @return boolean
	 * @throws BankApplicationException
	 */
	protected String addNewCustomerID(Customer customer, int accountID) throws BankApplicationException {
		String generatedAccountID = BankApplicationUtils.generateAccountIDString(accountID);
		
		Map<String, Object> generateParamMap = new HashMap<>();		
		generateParamMap.put(BankApplicationConstants.ACCOUNT_ID, generatedAccountID);
		generateParamMap.put(BankApplicationConstants.CUSTOMER_NAME, customer.getName());
		generateParamMap.put(BankApplicationConstants.BALANCE, customer.getBalance());
		
		bankDao.addNewCustomer(generateParamMap, BankQuery.ADD_CUSTOMER);
		
		return generatedAccountID;
	}

	@Override
	public BigDecimal getBalance(String accountID) throws BankApplicationException {
		if(!BankApplicationUtils.validateAccountID(accountID)) {
			throw new BankApplicationException("Invalid Account ID");
		}
		return getAccountBalance(accountID);
	}

	@Override
	public synchronized BigDecimal executeTransaction(Transaction transaction) throws BankApplicationException {
		Map<String, Object> paramMap = new HashMap<>();
		BigDecimal updatedBalance = getUpdatedBalance(transaction);
		
		paramMap.put(BankApplicationConstants.ACCOUNT_ID, transaction.getAccountID());
		paramMap.put(BankApplicationConstants.BALANCE, updatedBalance);

		template.execute(status -> {
			try {
				bankDao.doTransaction(paramMap, BankQuery.UPDATE_BALANCE);
				return true;
			} catch (Exception e) {
				status.setRollbackOnly();
				return false;
			}
		});
		
		bankDao.doTransaction(paramMap, BankQuery.UPDATE_BALANCE);
		
		return updatedBalance;
	}
	
	/**
	 * Returns the updated balance, after performing the transaction calculations.
	 * 
	 * @param transaction
	 * @return updated balance
	 * @throws BankApplicationException
	 */
	protected BigDecimal getUpdatedBalance(Transaction transaction) throws BankApplicationException {
		BigDecimal availableBalance = getAccountBalance(transaction.getAccountID());
		BigDecimal updatedBalance = null;
		
		if (transaction.getType().equals(TransactionType.DEPOSIT)) {
			updatedBalance = availableBalance.add(transaction.getAmount());
		} else if (transaction.getType().equals(TransactionType.WITHDRAWAL)) {
			if (availableBalance.compareTo(transaction.getAmount()) < 0){
				throw new BankApplicationException("Not enough balance to withdraw " + availableBalance);
			}
			updatedBalance = availableBalance.subtract(transaction.getAmount());
		}
		
		return updatedBalance;
	}
	
	/**
	 * Fetches and returns the account balance in the given account.
	 * 
	 * @param accountID
	 * @return balance
	 * @throws BankApplicationException
	 */
	private BigDecimal getAccountBalance(String accountID) throws BankApplicationException {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put(BankApplicationConstants.ACCOUNT_ID, accountID);
		
		return bankDao.getBalance(paramMap, BankQuery.GET_BALANCE);
	}
	
}
