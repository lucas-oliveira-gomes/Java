package br.com.bradseg.baixaalta.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BaixaDlqProcessor {
	private static final String HEADER_X_DEATH = "x-death";
	private static final Logger logger = LoggerFactory.getLogger(BaixaDlqProcessor.class);
	private static final String HEADER_X_RETRIES_COUNT = "x-retries-count";

	@Value("${spring.mq.dlq.max_retries}")
	private Integer maxRetries;
	@Value("${spring.mq.exchanges.parking_lot}")
	private String parkingLotExchange;
	@Value("${spring.mq.exchanges.default}")
	private String baixaDefaultExchange;
	@Autowired
	private RabbitTemplate rabbitTemplate;

	@RabbitListener(queues = { "${spring.mq.filas.dead_letter}" })
	public void processarDlq(Message msgFalhada) {
		msgFalhada.getMessageProperties().getHeaders().remove(HEADER_X_DEATH);
		Integer retriesCount = (Integer) msgFalhada.getMessageProperties().getHeaders().get(HEADER_X_RETRIES_COUNT);
		if (retriesCount == null)
			retriesCount = 1;
		if (retriesCount > maxRetries) {
			logger.info("Mensagem [{}] sendo enviada para o ponto de parada!", msgFalhada);
			rabbitTemplate.send(parkingLotExchange, msgFalhada.getMessageProperties().getReceivedRoutingKey(),
					msgFalhada);
			return;
		}
		logger.info("Tentando a mensagem [{}] pela {} vez", msgFalhada, retriesCount);
		msgFalhada.getMessageProperties().getHeaders().put(HEADER_X_RETRIES_COUNT, ++retriesCount);
		rabbitTemplate.send(baixaDefaultExchange, msgFalhada.getMessageProperties().getReceivedRoutingKey(),
				msgFalhada);
	}
}
