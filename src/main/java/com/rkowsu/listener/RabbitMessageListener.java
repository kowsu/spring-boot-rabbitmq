package com.rkowsu.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMessageListener {

    @RabbitListener(queues = "${rk.amqp.direct.queue-name}")
    public void directMessageListener(Message message) {
        System.out.println("Direct Received " + new String(message.getBody()));
    }

    @RabbitListener(queues = "${rk.amqp.fanOut.queue-name}")
    public void fanOutMessageListener(Message message) {
        System.out.println("FanOut Received " + new String(message.getBody()));
    }
    @RabbitListener(queues = "electronics-fanOut-queue1")
    public void fanOut1MessageListener(Message message) {
        System.out.println("FanOut1 Received " + new String(message.getBody()));
    }
}
