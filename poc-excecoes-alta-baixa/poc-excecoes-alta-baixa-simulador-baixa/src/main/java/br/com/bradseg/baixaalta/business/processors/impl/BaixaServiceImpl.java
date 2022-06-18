package br.com.bradseg.baixaalta.business.processors.impl;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import br.com.bradseg.baixaalta.business.processors.BaixaService;
import br.com.bradseg.baixaalta.dao.SubInformacaoDAO;

@Service
public class BaixaServiceImpl implements BaixaService {
	private static final Logger logger = LoggerFactory.getLogger(BaixaServiceImpl.class);

	@Value("${spring.mq.message.size:4096}")
	private long msgSize;
	@Autowired
	private SubInformacaoDAO subInfoDao;

	@RabbitListener(queues = { "${spring.mq.filas.baixa}" })
	public void processar(int informacaoId) throws DataAccessException {
		int subInfoId = subInfoDao.insertSubInfo(informacaoId, message(msgSize));
		logger.info("Inseriu subInfoId = {}", subInfoId);
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
