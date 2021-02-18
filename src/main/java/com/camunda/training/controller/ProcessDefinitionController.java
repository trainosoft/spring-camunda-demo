package com.camunda.training.controller;

import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.camunda.training.pojo.Response;

@RestController
public class ProcessDefinitionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDefinitionController.class);

	@Autowired
	private RuntimeService runtimeService;

	@PostMapping("/start/{processKey}")
	public Response startProcess(@RequestBody Map<String, Object> processVars, @PathVariable("processKey") String processKey) {

		LOGGER.info("Starting process {}", processKey);
		Response response = new Response();
		ProcessInstance processInstance = null;
		try {
			processInstance = runtimeService.startProcessInstanceByKey(processKey, processVars);
		} catch (Exception exception) {
			response.setCode(500);
			response.setMessage("Process could not started");
			response.setException(exception.getMessage());
			return response;
		}

		response.setCode(200);
		response.setData(processInstance.getProcessInstanceId());
		response.setMessage("Process started successfully");

		return response;

	}
}
