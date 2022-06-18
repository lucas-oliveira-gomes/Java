package br.com.bradseg.baixaalta.web.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bradseg.baixaalta.business.exception.ErroSimuladoException;
import br.com.bradseg.baixaalta.dao.InformacaoDAO;
import br.com.bradseg.baixaalta.web.services.AltaService;

@Service
public class AltaServiceImpl implements AltaService {
	private static Logger logger = LoggerFactory.getLogger(AltaServiceImpl.class);

	@Value("${spring.mq.filas.baixa}")
	private String baixaQ;
	@Value("${spring.mq.exchanges.default}")
	private String baixaDfltExge;
	@Value("${spring.mq.message.size:4096}")
	private long msgSize;

	@Autowired
	private InformacaoDAO informacaoDAO;
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	@Transactional(rollbackFor = ErroSimuladoException.class)
	public List<Integer> simularAltaPlataforma(Integer qtdInserts, Boolean simularErro) {
		ArrayList<Integer> toReturn = new ArrayList<>();
		for (int i = 0; i < qtdInserts; i++) {
			int insertedId = informacaoDAO.insertInformacao(message(msgSize));
			rabbitTemplate.convertAndSend(baixaDfltExge, baixaQ, insertedId);
			toReturn.add(insertedId);
			if (simularErro && (i + 1) == qtdInserts) {
				throw new ErroSimuladoException();
			}
		}
		logger.info("Inseridos {} registros", qtdInserts);
		return toReturn;
	}

	private String message(long size) {
		int asciiSize = 26;
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			char c = (char) (r.nextInt(asciiSize) + 'a');
			sb.append(c);
		}
		return sb.toString();
	}
}
