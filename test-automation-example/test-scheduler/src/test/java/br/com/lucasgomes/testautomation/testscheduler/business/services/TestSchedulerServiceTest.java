package br.com.lucasgomes.testautomation.testscheduler.business.services;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.lucasgomes.testautomation.testscheduler.business.services.impl.TestSchedulerServiceImpl;

class TestSchedulerServiceTest {

	private TestSchedulerService testSchedulerService;
	private Set<String> classesIds;

	@Test
	void testCreateTestFor() {
		testSchedulerService.createTestFor(classesIds);
		fail("Not yet implemented");
	}

	@BeforeEach
	void setup() {
		testSchedulerService = new TestSchedulerServiceImpl();
		classesIds = Arrays.asList("abc", "def", "ghi").stream().collect(Collectors.toSet());
	}
}
