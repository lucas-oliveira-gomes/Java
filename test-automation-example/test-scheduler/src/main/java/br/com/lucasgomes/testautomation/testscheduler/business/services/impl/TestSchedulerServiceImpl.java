package br.com.lucasgomes.testautomation.testscheduler.business.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.lucasgomes.repository.repositories.TestClassRepository;
import br.com.lucasgomes.repository.repositories.TestRepository;
import br.com.lucasgomes.testautomation.model.Test;
import br.com.lucasgomes.testautomation.model.TestClass;
import br.com.lucasgomes.testautomation.testscheduler.business.exceptions.TestScheduleException;
import br.com.lucasgomes.testautomation.testscheduler.business.services.TestSchedulerService;

@Service
public class TestSchedulerServiceImpl implements TestSchedulerService {

	@Autowired
	private TestClassRepository testClassRepository;

	@Autowired
	private TestRepository testRepository;

	@Override
	public Test createTestFor(Set<String> testClassesIds) throws TestScheduleException {
		try {
			Set<TestClass> testClassesFound = new HashSet<>();
			testClassRepository.findAllById(testClassesIds).forEach(testClassesFound::add);
			Test test = new Test();
			test.setTestClasses(testClassesFound);
			return testRepository.save(test);
		} catch (IllegalArgumentException iae) {
			throw new TestScheduleException(iae);
		}
	}
}
