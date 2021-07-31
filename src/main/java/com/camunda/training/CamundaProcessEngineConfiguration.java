package com.camunda.training;

import java.io.IOException;

import javax.sql.DataSource;

import org.camunda.bpm.engine.spring.ProcessEngineFactoryBean;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.engine.spring.SpringProcessEngineServicesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Import(SpringProcessEngineServicesConfiguration.class)
public class CamundaProcessEngineConfiguration {
	private static final Logger LOGGER = LoggerFactory.getLogger(CamundaProcessEngineConfiguration.class);

	@Value("${camunda.bpm.history-level:none}")
	private String historyLevel;

	// add more configuration here    
	// ---------------------------

	// configure data source via application.properties
	@Autowired
	private DataSource dataSource;

	@Bean
	public SpringProcessEngineConfiguration processEngineConfiguration() throws IOException {
		LOGGER.debug("Process engine configuration is being code updated  ");
		SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();

		config.setDataSource(dataSource);
		config.setDatabaseSchemaUpdate("true");

		config.setTransactionManager(transactionManager());

		config.setHistory(historyLevel);

		config.setJobExecutorActivate(true);
		config.setMetricsEnabled(false);

		return config;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		LOGGER.debug("Configuring transection manager");

		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public ProcessEngineFactoryBean processEngine() throws IOException {
		LOGGER.debug("Configuring process engine");

		ProcessEngineFactoryBean factoryBean = new ProcessEngineFactoryBean();
		factoryBean.setProcessEngineConfiguration(processEngineConfiguration());
		return factoryBean;
	}

}
