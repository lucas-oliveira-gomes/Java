package br.com.lucasgomes.testautomation.testscheduler.configurations;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigurations {

	@Value("${spring.mq.queue.runner}")
	private String runnerQueue;
	@Value("${spring.mq.queue.dlq}")
	private String dlqQueue;
	@Value("${spring.mq.exchange.dlq}")
	private String dqlExchange;
	@Value("${spring.mq.exchange.default}")
	private String defaultExchange;

	@Bean
	public RabbitTemplate rabbitTemplate(@Autowired ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setExchange(defaultExchange);
		template.setRoutingKey(runnerQueue);
		return template;
	}

	@Bean
	public Queue runnerQueue() {
		return QueueBuilder.durable(runnerQueue).withArgument("x-dead-letter-exchange", dqlExchange).build();
	}

	@Bean
	public Queue deadLetterQueue() {
		return QueueBuilder.durable(dlqQueue).build();
	}

	@Bean
	public DirectExchange defaultMessageExchange() {
		return new DirectExchange(defaultExchange);
	}

	@Bean
	public FanoutExchange deadLetterExchange() {
		return new FanoutExchange(dqlExchange);
	}

	@Bean
	public Binding bindingBaixaQueue() {
		return BindingBuilder.bind(runnerQueue()).to(defaultMessageExchange()).with(runnerQueue);
	}

	@Bean
	public Binding deadLetterBinding() {
		return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
	}
}
