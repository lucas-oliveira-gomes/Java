package br.com.lucasgomes.testautomation.testscheduler.web.controlleradvices;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.lucasgomes.testautomation.testscheduler.business.exceptions.TestScheduleException;

@ControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(TestScheduleException.class)
	public ResponseEntity<ErrorMessage> handleError(Exception ex) {
		return ResponseEntity.internalServerError().contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(ex));
	}

	@JsonInclude(Include.NON_NULL)
	final protected class ErrorMessage implements Serializable {

		private static final long serialVersionUID = 6793020362824370744L;

		final private UUID messageId = UUID.randomUUID();
		private String message;

		public ErrorMessage(String message) {
			super();
			this.message = message;
		}

		public ErrorMessage(Exception ex) {
			this(ex.getMessage());
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public UUID getMessageId() {
			return messageId;
		}
	}
}
