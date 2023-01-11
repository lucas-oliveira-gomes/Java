package br.com.lucasgomes.testautomation.testscheduler.web.controllers;

import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.lucasgomes.testautomation.testscheduler.business.exceptions.TestScheduleException;
import br.com.lucasgomes.testautomation.testscheduler.business.facades.TestSchedulerFacade;

@WebMvcTest(TestSchedulerWebService.class)
class TestSchedulerWebServiceTest {
	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper bjectMapper;

	@MockBean
	private TestSchedulerFacade scheduler;

	private Set<String> classesIds;

	@Test
	void testSchedule() throws Exception {
		when(scheduler.schedule(anySet()))
				.thenReturn(new br.com.lucasgomes.testautomation.model.Test());

		mvc.perform(post("/schedule")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(bjectMapper.writeValueAsString(classesIds)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.testExecutionStep").value("SCHEDULED"));

		verify(scheduler, times(1)).schedule(anySet());
	}
	
	@Test
	void testScheduleError() throws Exception {
		String errorMessage = "TestException";
		when(scheduler.schedule(anySet())).thenThrow(new TestScheduleException(new RuntimeException(errorMessage)));
		
		mvc.perform(post("/schedule")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(bjectMapper.writeValueAsString(classesIds)))
			.andExpect(status().isInternalServerError())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.message").value("java.lang.RuntimeException: " + errorMessage));

	verify(scheduler, times(1)).schedule(anySet());
	}

	@BeforeEach
	void setup() {
		classesIds = Arrays.asList("abc", "def", "ghi").stream().collect(Collectors.toSet());
	}
}
