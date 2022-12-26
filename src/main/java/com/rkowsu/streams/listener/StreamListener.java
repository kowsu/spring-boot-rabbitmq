package com.rkowsu.streams.listener;

/*
    @created /26/2022 - 9:29 PM
    @project spring-boot-rabbitmq
    @author k.ramanjineyulu
*/

import com.rabbitmq.stream.ConsumerBuilder;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class StreamListener {

    @PostConstruct
    public void consume() {

        AtomicInteger atomicInteger = new AtomicInteger(0);
        Environment.builder()
                .host("localhost")
                .port(5552)
                .username("admin")
                .password("admin123A!")
                .build()
                .consumerBuilder()
                .stream("first-application-stream")
                .offset(OffsetSpecification.first())
                .messageHandler(((context, message) -> {
                    atomicInteger.incrementAndGet();
                    System.out.println(message.getBody());
                }));
        System.out.println("Total messages consumed " + atomicInteger.get());
    }


}
