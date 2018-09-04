package com.bank.model;

import java.math.BigDecimal;

/**
 * This class represents the user, 
 * that has attributes such as
 * - Username
 * - Account number
 * - Balance
 * 
 * @author BharatGoel
 *
 */
public class Customer {

	private String name;
	private String accountNumber;
	private BigDecimal balance;
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	private int num;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	public BigDecimal getBalance() {
		return balance;
	}
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}
