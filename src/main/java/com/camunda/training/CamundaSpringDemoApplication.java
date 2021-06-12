package com.camunda.training;

import java.util.List;

import javax.annotation.PostConstruct;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.history.HistoryLevel;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CamundaSpringDemoApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(CamundaSpringDemoApplication.class);

	@Autowired
	private RuntimeService runtimeService;
	
	public static void main(String[] args) {
		SpringApplication.run(CamundaSpringDemoApplication.class, args);
	}

	@PostConstruct
	public void getProcessIsntances() {
		List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().active().list();
		
		for(ProcessInstance processInstance : processInstances) {
			LOGGER.info("Process instsance id 123123: {}", processInstance.getId())
			LOGGER.info("Process instsance definition id : {}", processInstance.getProcessDefinitionId());

		}
	}
}
