package br.com.lucasgomes.testautomation.testscheduler.business.exceptions;

public class TestScheduleException extends RuntimeException {

	private static final long serialVersionUID = -1990802420886999925L;

	public TestScheduleException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public TestScheduleException(String arg0) {
		super(arg0);
	}

	public TestScheduleException(Throwable arg0) {
		super(arg0);
	}

}
