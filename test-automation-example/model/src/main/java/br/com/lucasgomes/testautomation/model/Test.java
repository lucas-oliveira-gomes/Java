package br.com.lucasgomes.testautomation.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "tests")
@JsonInclude(Include.NON_NULL)
public class Test implements Serializable {
	private static final long serialVersionUID = 6519535734814321152L;

	@Id
	private UUID id = UUID.randomUUID();
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime creationTime = LocalDateTime.now();
	private TestExecutionStep testExecutionStep = TestExecutionStep.SCHEDULED;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "execution_results_id", referencedColumnName = "id")
	private ExecutionResult executionResult;
	@ManyToMany
	@JoinTable(name = "test_test_classes", joinColumns = @JoinColumn(name = "test_id"), inverseJoinColumns = @JoinColumn(name = "test_class_id"))
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
		Test other = (Test) obj;
		return Objects.equals(id, other.id);
	}

}
