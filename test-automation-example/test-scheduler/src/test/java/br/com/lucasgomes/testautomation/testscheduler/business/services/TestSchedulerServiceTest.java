package br.com.lucasgomes.testautomation.testscheduler.business.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.lucasgomes.repository.repositories.TestClassRepository;
import br.com.lucasgomes.repository.repositories.TestRepository;
import br.com.lucasgomes.testautomation.testscheduler.business.exceptions.TestScheduleException;
import br.com.lucasgomes.testautomation.testscheduler.business.services.impl.TestSchedulerServiceImpl;

@ExtendWith(MockitoExtension.class)
class TestSchedulerServiceTest {

	@Mock
	private TestClassRepository testClassRepository;

	@Mock
	private TestRepository testRepository;

	@InjectMocks
	private TestSchedulerServiceImpl testSchedulerService;

	private Set<String> classesIds;

	@Test
	void testCreateTestFor() {
		testSchedulerService.createTestFor(classesIds);
		verify(testClassRepository, times(1)).findAllById(anyIterable());
		verify(testRepository, times(1)).save(any());
	}

	@Test
	void testCreateTestFor_WhenError() {
		when(testClassRepository.findAllById(anyIterable())).thenThrow(new IllegalArgumentException());
		assertThrows(TestScheduleException.class, () -> testSchedulerService.createTestFor(classesIds));
		
		verify(testClassRepository, times(1)).findAllById(anyIterable());
		verifyNoInteractions(testRepository);
	}

	@BeforeEach
	void setup() {
		classesIds = Arrays.asList("abc", "def", "ghi").stream().collect(Collectors.toSet());
	}
}
