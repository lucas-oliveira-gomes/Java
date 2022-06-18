package br.com.bradseg;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.ConditionalRejectingErrorHandler;
import org.springframework.amqp.rabbit.listener.FatalExceptionStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;

import br.com.bradseg.baixaalta.business.processors.BaixaCustomFatalExceptionStrategy;

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
	SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
			SimpleRabbitListenerContainerFactoryConfigurer configurer) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		factory.setErrorHandler(errorHandler());
		return factory;
	}

	@Bean
	ErrorHandler errorHandler() {
		return new ConditionalRejectingErrorHandler(baixaCustomFatalExceptionStrategy());
	}

	@Bean
	FatalExceptionStrategy baixaCustomFatalExceptionStrategy() {
		return new BaixaCustomFatalExceptionStrategy();
	}

	@Bean
	public Queue baixaQueue() {
		return QueueBuilder.durable(baixaQ).withArgument("x-dead-letter-exchange", baixaDlqExchange).build();
	}

	@Bean
	public Queue deadLetterQueue() {
		return QueueBuilder.durable(deadLetterQ).build();
	}

	@Bean
	public Queue parkingLotQueue() {
		return QueueBuilder.durable(parkingLotQ).build();
	}

	@Bean
	public DirectExchange baixaMessageExchange() {
		return new DirectExchange(baixaDefaultExchange);
	}

	@Bean
	public FanoutExchange deadLetterExchange() {
		return new FanoutExchange(baixaDlqExchange);
	}

	@Bean
	public FanoutExchange parkingLotExchange() {
		return new FanoutExchange(parkingLotExchange);
	}

	@Bean
	public Binding bindingBaixaQueue() {
		return BindingBuilder.bind(baixaQueue()).to(baixaMessageExchange()).with(baixaQ);
	}

	@Bean
	public Binding deadLetterBinding() {
		return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
	}

	@Bean
	public Binding parkingLotBinding() {
		return BindingBuilder.bind(parkingLotQueue()).to(parkingLotExchange());
	}
}
