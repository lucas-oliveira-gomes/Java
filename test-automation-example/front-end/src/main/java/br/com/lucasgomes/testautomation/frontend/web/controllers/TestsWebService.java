package br.com.lucasgomes.testautomation.frontend.web.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestsWebService {
	
	@GetMapping(path = "/tests", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> tests() {
		return ResponseEntity.ok(new Object());
	}
	
	@GetMapping(path = "/test/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> testByName(@PathVariable String name) {
		return ResponseEntity.ok(new Object());
	}
}
