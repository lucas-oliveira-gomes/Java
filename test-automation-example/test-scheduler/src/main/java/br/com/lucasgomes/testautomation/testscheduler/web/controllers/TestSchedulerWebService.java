package br.com.lucasgomes.testautomation.testscheduler.web.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.lucasgomes.testautomation.model.Test;
import br.com.lucasgomes.testautomation.testscheduler.business.facades.TestSchedulerFacade;

@RestController
public class TestSchedulerWebService {
	@Autowired
	private TestSchedulerFacade scheduler;

	@PostMapping(path = "/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Test> schedule(@RequestBody Set<String> testClassesIds) {
		return ResponseEntity.status(HttpStatus.CREATED).body(scheduler.schedule(testClassesIds));
	}
}
