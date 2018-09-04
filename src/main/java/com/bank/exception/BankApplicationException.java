package com.bank.exception;

/**
 * This class represents custom exception for
 * Bank Application.
 * 
 * @author BharatGoel
 *
 */
public class BankApplicationException extends Exception {
	
	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	public BankApplicationException(String message) {
		super(message);
	}

}
