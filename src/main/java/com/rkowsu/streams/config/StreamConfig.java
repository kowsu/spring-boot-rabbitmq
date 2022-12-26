package com.rkowsu.streams.config;


import com.rabbitmq.stream.ConsumerBuilder;
import com.rabbitmq.stream.Environment;
import com.rabbitmq.stream.OffsetSpecification;
import com.rabbitmq.stream.Producer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StreamConfig {

    public Environment environment() {
       return Environment.builder()
               .host("localhost")
               .port(5552)
               .username("admin")
               .password("admin123A!")
               .build();
    }

    @Bean
    public Producer producer() {
        return environment()
                .producerBuilder()
                .stream("first-application-stream")
                .build();
    }

}
