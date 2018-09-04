	package com.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bank.exception.BankApplicationException;
import com.bank.model.Customer;
import com.bank.model.Transaction;
import com.bank.service.BankApplicationService;

/**
 * Implementation of controller layer for
 * Bank Application.
 * 
 * This class exposes APIs for:
 * - Adding new customer
 * - Check account balance
 * - Execute a transaction
 * 
 * @author BharatGoel
 *
 */
@RestController
public class BankApplicationController {
	
	@Autowired
	BankApplicationService bankApplicationService;
	
	@RequestMapping(path = "/addcustomer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> addCustomer(@RequestBody Customer customer) {
		Object result = null;
		Customer customerResponse;
		ResponseEntity<Object> response;
		HttpStatus status = HttpStatus.OK;
		
		try {
			customerResponse = bankApplicationService.addNewCustomer(customer);
			if (customerResponse.getAccountNumber() == null) {
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				result = "Unable to add new customer " + customer.getName();
			} else {				
				result = "New customer: " + customerResponse.getName() + " added, with account number: " + customerResponse.getAccountNumber();
			}
		} catch (BankApplicationException e) {
			result = e.getMessage();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		response = new ResponseEntity<>(result, status);
		return response;	
	}
	
	@RequestMapping(path = "/checkbalance", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> checkBalance(@RequestBody String accountID) {
		Object result = null;
		ResponseEntity<Object> response;
		HttpStatus status = HttpStatus.OK;
		
		try {
			result = bankApplicationService.getBalance(accountID);
		} catch (NumberFormatException | BankApplicationException e) {
			result = e.getMessage();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		response = new ResponseEntity<>(result, status);
		return response;	
	}
	
	@RequestMapping(path = "/dotransaction", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Object> doTransaction(@RequestBody Transaction transaction) {
		Object result = null;
		ResponseEntity<Object> response;
		HttpStatus status = HttpStatus.OK;
		
		try {
			result = bankApplicationService.executeTransaction(transaction);
		} catch (BankApplicationException e) {
			result = e.getMessage();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		
		response = new ResponseEntity<>(result, status);
		return response;	
	}

}
