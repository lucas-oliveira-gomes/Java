package br.com.lucasgomes.testautomation.testscheduler.business.facades;

import java.util.Set;

import br.com.lucasgomes.testautomation.model.Test;
import br.com.lucasgomes.testautomation.testscheduler.business.exceptions.TestScheduleException;

public interface TestSchedulerFacade {
	/**
	 Schedules a test for future exection
	 * @param testClassesIds - Test Classes that will be run
	 * @return Scheduled test details
	 * @throws TestScheduleException if there is a problem
	 */
	Test schedule(Set<String> testClassesIds) throws TestScheduleException;
}
