package com.rkowsu.config.properties;

@lombok.Data
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
public class EventProperties {
    private String exchangeName;
    private String queueName;
    private String routingKey;
}