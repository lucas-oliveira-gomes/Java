package br.com.lucasgomes.testautomation.testscheduler.business.services;

import java.util.Set;

import br.com.lucasgomes.testautomation.model.Test;
import br.com.lucasgomes.testautomation.testscheduler.business.exceptions.TestScheduleException;

public interface TestSchedulerService {

	/**
	 * Create an entry for a new test with the given test classes ids
	 * @param testClassesIds - classes ids for this test
	 * @return Created test details
	 * @throws TestScheduleException - if there is a problem creating the test
	 */
	Test createTestFor(Set<String> testClassesIds) throws TestScheduleException;

}
