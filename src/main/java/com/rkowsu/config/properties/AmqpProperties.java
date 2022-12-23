package com.rkowsu.config.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "rk.amqp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AmqpProperties {

    private Direct direct;
    private Topic topic;
    private FanOut fanOut;
    private Header header;

}
