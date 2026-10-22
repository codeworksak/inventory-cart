package com.codeworks.inventorycart.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.rabbitmq")
@Data
public class CustomRabbitProperties {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String queueName1;
    private String exchangeName;
    private String routingKey;
    private String queueOrder;
}
