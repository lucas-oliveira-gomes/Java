package br.com.bradseg.baixaalta.business.advices;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.bradseg.baixaalta.business.exception.ErroSimuladoException;

@ControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ ErroSimuladoException.class })
	public ResponseEntity<Map<String, Object>> lidaComErro(Exception ex) {
		Map<String, Object> toReturn = new HashMap<>();
		toReturn.put("errorId", UUID.randomUUID());
		toReturn.put("errorMessage", ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(toReturn);
	}
}
