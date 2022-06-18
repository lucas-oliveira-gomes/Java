package br.com.bradseg.baixaalta.business.processors;

import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler.DefaultExceptionStrategy;
import org.springframework.dao.DataAccessException;

public class BaixaCustomFatalExceptionStrategy extends DefaultExceptionStrategy {
	@Override
	public boolean isFatal(Throwable t) {
		return (t.getCause() instanceof DataAccessException);
	}
}
