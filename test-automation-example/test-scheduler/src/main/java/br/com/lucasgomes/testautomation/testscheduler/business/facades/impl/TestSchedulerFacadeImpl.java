package br.com.lucasgomes.testautomation.testscheduler.business.facades.impl;

import java.util.Set;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.lucasgomes.testautomation.model.Test;
import br.com.lucasgomes.testautomation.testscheduler.business.exceptions.TestScheduleException;
import br.com.lucasgomes.testautomation.testscheduler.business.facades.TestSchedulerFacade;
import br.com.lucasgomes.testautomation.testscheduler.business.services.TestSchedulerService;

@Service
public class TestSchedulerFacadeImpl implements TestSchedulerFacade {
	@Value("${spring.mq.queue.testrunner}")
	private String testRunnerQueue;
	@Value("${spring.mq.exchanges.testrunner}")
	private String testRunnerExchange;

	@Autowired
	private TestSchedulerService testSchedulerService;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public Test schedule(Set<String> testClassesIds) throws TestScheduleException {
		Test createdTest = testSchedulerService.createTestFor(testClassesIds);
		try {
			rabbitTemplate.convertAndSend(testRunnerExchange, testRunnerQueue, createdTest.getId());
		} catch (AmqpException ex) {
			throw new TestScheduleException(ex);
		}
		return createdTest;
	}

}
