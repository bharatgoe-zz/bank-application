package com.bank.dao;

/**
 * Represents the queries used in this application.
 * 
 * @author BharatGoel
 *
 */
public interface BankQuery {
	
	/**
	 * Adding new customer
	 */
	public static final String ADD_CUSTOMER = "INSERT INTO ACCOUNT_INFO VALUES (nextval('bank_seq'),:acc_id,:cust_name,:balance)";

	/**
	 * Updates balance in given account ID
	 */
	public static final String UPDATE_BALANCE = "UPDATE ACCOUNT_INFO set tran_amount = :balance where acc_number= :acc_id";

	/**
	 * Fetches balance for given account ID
	 */
	public static final String GET_BALANCE = "SELECT tran_amount from  ACCOUNT_INFO where acc_number= :acc_id";
	
	/**
	 * Fetches generated account ID for new customer
	 */
	public static final String GENERATE_ACC_ID = "SELECT acc_id FROM acc_id_generator where param_key= :param_key";

	/**
	 * Updates generated account ID for new customer in the database 
	 */
	public static final String UPDATE_ACC_ID = "UPDATE acc_id_generator set ACC_ID= :acc_id where param_key= :param_key";

}
