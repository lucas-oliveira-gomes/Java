package br.com.bradseg.baixaalta.web.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bradseg.baixaalta.web.services.AltaService;

@RestController
public class ApiController {

	@Autowired
	private AltaService altaService;

	@GetMapping(path = "/simular", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> simular(
			@RequestParam(name = "qtdDados", defaultValue = "50") Integer qtdDados,
			@RequestParam(name = "simularErro", defaultValue = "FALSE") Boolean simularErro) {
		Map<String, Object> result = new HashMap<>();
		result.put("qtdDados", qtdDados);
		result.put("simularErro", simularErro);
		List<Integer> idsInseridos = altaService.simularAltaPlataforma(qtdDados, simularErro);
		result.put("idsInseridos", idsInseridos);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
