package com.rkowsu.producer;

import com.rkowsu.config.properties.AmqpProperties;
import com.rkowsu.event.model.Electronics;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class RabbitMessageProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private AmqpProperties amqpProperties;

    public void directExchange() {
        amqpTemplate.convertAndSend(amqpProperties.getDirect().getExchangeName(),
                amqpProperties.getDirect().getRoutingKey(), electronicProduct("DIRECT"));
    }

    public void fanOutExchange() {
        amqpTemplate.convertAndSend(amqpProperties.getFanOut().getExchangeName(), "",
                electronicProduct("FAN_OUT"));
    }

    public void topicExchange() {
        /*amqpTemplate.convertAndSend("company-topic-exchange", "queue.admin",
                electronicProduct("TOPIC"));*/
        amqpTemplate.convertAndSend("company-topic-exchange", "queue.finance",
                electronicProduct("TOPIC"));
    }


    public void headerExchange() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("newsCategory", "entertainment");
        messageProperties.setHeader("content_type", "application/json");

        MessageConverter messageConverter = new SimpleMessageConverter();
        Message message = messageConverter.toMessage(electronicProduct("HEADERS"), messageProperties);
        amqpTemplate.send("news-headers-exchange", "", message);
    }

    public void deadLetterExchange() {
        amqpTemplate.convertAndSend("abnormal-exchange", "abnormal-routing-key",
                electronicProduct("DIRECT"));
    }


    public Electronics electronicProduct(String type) {
        Electronics electronics = new Electronics("China", "8GB", "Octa");
        electronics.setId(type + "-" + UUID.randomUUID().toString());
        electronics.setDeliveryDate(new Date());
        electronics.setOrderedDate(new Date());
        return electronics;
    }
}
