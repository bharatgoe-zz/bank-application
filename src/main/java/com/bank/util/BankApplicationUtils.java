package com.bank.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * Utility class for Bank Application.
 * 
 * @author BharatGoel
 *
 */
public class BankApplicationUtils {
	
	private BankApplicationUtils() {
		
	}
	
	/**
	 * Converts integer account ID to a 6-digit string.
	 * 
	 * @param accountID
	 * @return
	 */
	public static String generateAccountIDString(int accountID) {
		return StringUtils.leftPad(String.valueOf(accountID), 6, "0");
	}
	
	/**
	 * Validates the entered accountID.
	 * It should be a 6-digit string, consisting of numbers only
	 * 
	 * @param accountID
	 * @return
	 */
	public static boolean validateAccountID(String accountID) {
		if (accountID.length() != 6) {
			return false;
		}
		
		Pattern pattern = Pattern.compile(BankApplicationConstants.ID_PATTERN);
		Matcher matcher = pattern.matcher(accountID);
		
		if (!matcher.matches()) {
			return false;
		}
		
		return true;
	}

}
