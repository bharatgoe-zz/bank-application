package com.bank.util;

import static org.junit.Assert.*;

import org.junit.Test;

import com.bank.util.BankApplicationUtils;

public class BankApplicationUtilsTest {

	@Test
	public void testGenerateAccountIDString() {
		int accountID = 3;
		String accountIDString = BankApplicationUtils.generateAccountIDString(accountID);
		assertTrue(accountIDString.equals("000003"));
	}
	
	@Test
	public void testValidateAccountID() {
		String accountID = "000036";
		assertTrue(BankApplicationUtils.validateAccountID(accountID));
		
		accountID = "ABGV&&";
		assertFalse(BankApplicationUtils.validateAccountID(accountID));
		
		accountID = "7800";
		assertFalse(BankApplicationUtils.validateAccountID(accountID));
	}

}
