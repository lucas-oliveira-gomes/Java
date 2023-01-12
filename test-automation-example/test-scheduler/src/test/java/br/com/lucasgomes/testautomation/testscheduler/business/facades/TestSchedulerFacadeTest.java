package br.com.lucasgomes.testautomation.testscheduler.business.facades;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import br.com.lucasgomes.testautomation.testscheduler.business.exceptions.TestScheduleException;
import br.com.lucasgomes.testautomation.testscheduler.business.facades.impl.TestSchedulerFacadeImpl;
import br.com.lucasgomes.testautomation.testscheduler.business.services.TestSchedulerService;

@ExtendWith(MockitoExtension.class)
class TestSchedulerFacadeTest {

	@InjectMocks
	private TestSchedulerFacadeImpl testSchedulerFacade;
	
	@Mock
	private TestSchedulerService testSchedulerService;	

	@Mock
	private RabbitTemplate rabbitTemplate;

	private Set<String> classesIds;

	@BeforeEach
	void setUp() {
		classesIds = Arrays.asList("abc", "def", "ghi").stream().collect(Collectors.toSet());
	}

	@Test
	void testSchedule() {
		when(testSchedulerService.createTestFor(anySet())).thenReturn(new br.com.lucasgomes.testautomation.model.Test());
		
		testSchedulerFacade.schedule(classesIds);
		
		verify(testSchedulerService, times(1)).createTestFor(anySet());
		verify(rabbitTemplate, times(1)).convertAndSend(any(UUID.class));
	}
	
	@Test
	void testSchedule_WhenServiceError() {
		when(testSchedulerService.createTestFor(anySet())).thenThrow(new TestScheduleException(""));
		
		assertThrows(TestScheduleException.class, () -> testSchedulerFacade.schedule(classesIds));		
		
		verify(testSchedulerService, times(1)).createTestFor(anySet());
		verifyNoInteractions(rabbitTemplate);
	}
	
	@Test
	void testSchedule_WhenRabbitError() {
		when(testSchedulerService.createTestFor(anySet())).thenReturn(new br.com.lucasgomes.testautomation.model.Test());
		doThrow(new AmqpException("")).when(rabbitTemplate).convertAndSend(any(UUID.class));
		
		assertThrows(TestScheduleException.class, () -> testSchedulerFacade.schedule(classesIds));
		
		verify(testSchedulerService, times(1)).createTestFor(anySet());
		verify(rabbitTemplate, times(1)).convertAndSend(any(UUID.class));
	}
}
