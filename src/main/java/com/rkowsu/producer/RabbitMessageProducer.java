package com.rkowsu.producer;

import com.rkowsu.config.properties.AmqpProperties;
import com.rkowsu.event.model.Electronics;
import org.springframework.amqp.core.AmqpTemplate;
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


    public Electronics electronicProduct(String type) {
        Electronics electronics = new Electronics("China", "8GB", "Octa");
        electronics.setId(type + "-" + UUID.randomUUID().toString());
        electronics.setDeliveryDate(new Date());
        electronics.setOrderedDate(new Date());
        return electronics;
    }
}
