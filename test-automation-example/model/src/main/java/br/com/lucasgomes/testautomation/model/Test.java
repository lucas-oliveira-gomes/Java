package br.com.lucasgomes.testautomation.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@JsonInclude(Include.NON_NULL)
public class Test implements Serializable {
	private static final long serialVersionUID = 6519535734814321152L;

	@Id
	private UUID id = UUID.randomUUID();
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime creationTime = LocalDateTime.now();
	private TestExecutionStep testExecutionStep = TestExecutionStep.SCHEDULED;
	private ExecutionResult executionResult;
	private Set<TestClass> testClasses;

	public TestExecutionStep getTestExecutionStep() {
		return testExecutionStep;
	}

	public void setTestExecutionStep(TestExecutionStep testExecutionStep) {
		this.testExecutionStep = testExecutionStep;
	}

	public ExecutionResult getExecutionResult() {
		return executionResult;
	}

	public void setExecutionResult(ExecutionResult executionResult) {
		this.executionResult = executionResult;
	}

	public UUID getId() {
		return id;
	}

	public LocalDateTime getCreationTime() {
		return creationTime;
	}

	public Set<TestClass> getTestClasses() {
		return testClasses;
	}

	public void setTestClasses(Set<TestClass> testClasses) {
		this.testClasses = testClasses;
	}

}
