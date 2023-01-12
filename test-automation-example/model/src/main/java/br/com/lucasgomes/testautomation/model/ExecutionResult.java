package br.com.lucasgomes.testautomation.model;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "execution_results")
@JsonInclude(Include.NON_NULL)
public class ExecutionResult {
	@Id
	private UUID id = UUID.randomUUID();
	private TestResult testResult;
	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "exec_res_id")
	private Set<Screenshot> screenshots;
	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "exec_res_id")
	private Set<TestAssertion> testSummary;

	public TestResult getTestResult() {
		return testResult;
	}

	public void setTestResult(TestResult testResult) {
		this.testResult = testResult;
	}

	public Set<Screenshot> getScreenshots() {
		return screenshots;
	}

	public void setScreenshots(Set<Screenshot> screenshots) {
		this.screenshots = screenshots;
	}

	public Set<TestAssertion> getTestSummary() {
		return testSummary;
	}

	public void setTestSummary(Set<TestAssertion> testSummary) {
		this.testSummary = testSummary;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExecutionResult other = (ExecutionResult) obj;
		return Objects.equals(id, other.id);
	}
}
