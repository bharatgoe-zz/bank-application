package com.bank.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class BankTransactionTemplate {

	@Autowired
	@Qualifier("bankTransactionManager")
	DataSourceTransactionManager dataSourceTransactionManager;

	TransactionTemplate template;

	@Bean(name="bankTemplate")
	public TransactionTemplate transactionTemplate() {
		template = new TransactionTemplate(dataSourceTransactionManager);
		return template;
	}

}