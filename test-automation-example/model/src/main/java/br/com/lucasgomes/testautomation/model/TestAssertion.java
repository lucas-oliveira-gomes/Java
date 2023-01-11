package br.com.lucasgomes.testautomation.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TestAssertion implements Serializable {

	private static final long serialVersionUID = -6398898249266699229L;
	@Id
	private UUID id = UUID.randomUUID();
	private String assertion;
	private Boolean result;

	public String getAssertion() {
		return assertion;
	}

	public void setAssertion(String assertion) {
		this.assertion = assertion;
	}

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public UUID getId() {
		return id;
	}
}