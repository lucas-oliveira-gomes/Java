package br.com.bradseg;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfiguration {
	@Value("${spring.mq.filas.baixa}")
	private String baixaQ;
	@Value("${spring.mq.filas.dead_letter}")
	private String deadLetterQ;
	@Value("${spring.mq.filas.parking_lot}")
	private String parkingLotQ;
	@Value("${spring.mq.exchanges.default}")
	private String baixaDefaultExchange;
	@Value("${spring.mq.exchanges.dead_letter}")
	private String baixaDlqExchange;
	@Value("${spring.mq.exchanges.parking_lot}")
	private String parkingLotExchange;

	@Bean
	Queue baixaQueue() {
		return QueueBuilder.durable(baixaQ).withArgument("x-dead-letter-exchange", baixaDlqExchange).build();
	}

	@Bean
	Queue deadLetterQueue() {
		return QueueBuilder.durable(deadLetterQ).build();
	}

	@Bean
	Queue parkingLotQueue() {
		return QueueBuilder.durable(parkingLotQ).build();
	}

	@Bean
	DirectExchange baixaMessageExchange() {
		return new DirectExchange(baixaDefaultExchange);
	}

	@Bean
	FanoutExchange deadLetterExchange() {
		return new FanoutExchange(baixaDlqExchange);
	}

	@Bean
	FanoutExchange parkingLotExchange() {
		return new FanoutExchange(parkingLotExchange);
	}

	@Bean
	Binding bindingBaixaQueue() {
		return BindingBuilder.bind(baixaQueue()).to(baixaMessageExchange()).with(baixaQ);
	}

	@Bean
	Binding deadLetterBinding() {
		return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
	}

	@Bean
	Binding parkingLotBinding() {
		return BindingBuilder.bind(parkingLotQueue()).to(parkingLotExchange());
	}
}
