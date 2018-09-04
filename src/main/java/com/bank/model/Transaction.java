package com.bank.model;

import java.math.BigDecimal;

/**
 * This class represents a transaction.
 * It has attributes such as transaction type, and amount.
 * 
 * @author BharatGoel
 *
 */
public class Transaction {
	
	private TransactionType type;
	private BigDecimal amount;
	private String accountID;
	
	public String getAccountID() {
		return accountID;
	}

	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}

	public TransactionType getType() {
		return type;
	}
	
	public void setType(TransactionType type) {
		this.type = type;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
