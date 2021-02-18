package com.camunda.training.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.DecisionService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.camunda.training.pojo.Response;
import com.camunda.training.pojo.TaskDetail;

@RestController
public class TaskController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessDefinitionController.class);
	@Autowired
	private TaskService taskService;

	@Autowired
	private RuntimeService runtimeService;
	
	@GetMapping("/tasks/{assignee}")
	public Response listTasks(@PathVariable("assignee") String assignee) {
		LOGGER.info("Retrieving the task list for {}", assignee);
		
		Response response = new Response();
		List<Task> tasks = new ArrayList<Task>();
		List<TaskDetail> taskDetails = new ArrayList<TaskDetail>();
				
		try {
			tasks = taskService.createTaskQuery().active().taskAssignee(assignee).list();
			for(Task task: tasks) {
				TaskDetail taskDetail = new TaskDetail(); 
				taskDetail.setTaskId(task.getId());
				taskDetail.setTaskName(task.getName());
				taskDetail.setPriority(task.getPriority());
				taskDetail.setCreateTime(task.getCreateTime());
				taskDetails.add(taskDetail);
			}
			
		} catch (Exception exception) {
			response.setCode(500);
			response.setMessage("Task list could not retrieved");
			response.setException(exception.getMessage());
			return response;
		}
		response.setCode(200);
		response.setData(taskDetails);
		response.setMessage("Task list retrieved successfully");

		
		
		
		return response;

	}
}
