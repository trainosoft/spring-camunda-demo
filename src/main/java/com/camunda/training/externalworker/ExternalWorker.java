package com.camunda.training.externalworker;

import java.util.List;

import org.camunda.bpm.engine.ExternalTaskService;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExternalWorker {
	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalWorker.class);

	@Autowired
	private ExternalTaskService externalTaskService;

	private static String EXTERNAL_WORKER_ID = "Process_External_Task";
	private static String EXTERNAL_TASK_TOPIC = "topicNotificationPolicyCreated";

	@Scheduled(fixedRate = 3000)
	public void fetchExternalTask() throws InterruptedException {
		List<LockedExternalTask> lockedExternalTaskTasks = externalTaskService.fetchAndLock(1, EXTERNAL_WORKER_ID)
				.topic(EXTERNAL_TASK_TOPIC, 2000).execute();
		LOGGER.info("{} external tasks locked", lockedExternalTaskTasks.size());
		// do the work
		for (LockedExternalTask lockedTask : lockedExternalTaskTasks) {
			Thread.sleep(5000);

			// work on task for that topic
			LOGGER.info("Completing task : {}", lockedTask.getWorkerId());
			externalTaskService.complete(lockedTask.getId(), EXTERNAL_WORKER_ID, null);

		}
	}

}
