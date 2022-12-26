package com.rkowsu.config;

import com.rkowsu.config.properties.AmqpProperties;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.stream.Collectors;

@Configuration
public class RabbitMqConfig {

    public RabbitMqConfig() {
        super();
    }

    @Autowired
    private AmqpProperties amqpProperties;

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(amqpProperties.getDirect().getExchangeName());
    }

    @Bean
    public Queue electronicsQueue() {
        return new Queue(amqpProperties.getDirect().getQueueName());
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(amqpProperties.getFanOut().getExchangeName());
    }

    @Bean
    public Declarables queues() {
        return new Declarables(Arrays.stream(amqpProperties.getFanOut().getQueueName().split(","))
                .map(q -> new Queue(q, false, false, true))
                .collect(Collectors.toList())
                .toArray(new Queue[0]));
    }

    @Bean
    public Declarables fanOutBinding() {
        return new Declarables(
                Arrays.stream(amqpProperties.getFanOut().getQueueName().split(","))
                        .sequential()
                        .map(q -> BindingBuilder.bind(new Queue(q)).to(fanoutExchange()))
                        .collect(Collectors.toList()));
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(electronicsQueue()).to(directExchange()).with(amqpProperties.getDirect().getRoutingKey());
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange abnormalExchange() {
        return new DirectExchange("abnormal-exchange");
    }

    @Bean
    public DirectExchange dlqEchange() {
        return new DirectExchange("dlq-exchange");
    }

    @Bean
    public Queue dlq() {
        return new Queue("dlq");
    }


    @Bean
    public Queue abnormalQueue() {
        return QueueBuilder.durable("abnormal-queue")
                .withArgument("x-dead-letter-exchange", "dlq-exchange")
                .withArgument("x-dead-letter-routing-key", "deadLetter")
                .build();
    }

    @Bean
    public Binding abnormalBinding() {
        return BindingBuilder.bind(abnormalQueue()).to(abnormalExchange())
                .with("abnormal-routing-key");
    }


    @Bean
    public Binding dlqBinding() {
        return BindingBuilder.bind(dlq()).to(dlqEchange()).with("deadLetter");
    }
}
