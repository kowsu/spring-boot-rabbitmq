package com.rkowsu;

import com.rkowsu.producer.RabbitMessageProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootRabbitmqApplication implements CommandLineRunner {

    @Autowired
    private RabbitMessageProducer rabbitMessageProducer;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRabbitmqApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        for (int i = 0; i < 100; i++) {
            //Enable to test direct and fanOut, topic exchange model
            //rabbitMessageProducer.directExchange();
            //rabbitMessageProducer.fanOutExchange();
//            rabbitMessageProducer.topicExchange();
//            rabbitMessageProducer.headerExchange();
            rabbitMessageProducer.deadLetterExchange();
//        }
    }
}
