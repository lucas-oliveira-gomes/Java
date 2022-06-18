package br.com.bradseg.baixaalta.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class BaixaParkingLotProcessor {
	private static final Logger logger = LoggerFactory.getLogger(BaixaParkingLotProcessor.class);

	@RabbitListener(queues = { "${spring.mq.filas.parking_lot}" })
	public void processarparkingLot(Message msgFalhada) {
		logger.info("Mensagem [{}] nao processada. Notificando interessados.", msgFalhada);
	}
}
