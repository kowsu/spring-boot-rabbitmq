package com.rkowsu.streams.producer;

import com.rabbitmq.stream.Message;
import com.rabbitmq.stream.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StreamProducer {

    @Autowired
    Producer producer;

    @PostConstruct
    public void produce() {
        for (int i = 0; i < 1000; i++) {
            Message message = producer.messageBuilder()
                    .addData("Hello world".concat( " " + i).getBytes())
                    .build();
            producer.send(message, confirmationStatus -> System.out.println(confirmationStatus.getMessage().getBody()));
        }

    }

}
