package com.codeworks.inventorycart.configs;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties (prefix = "app.redis")
@Data
public class CustomRedisProperties {

    private String host;
    private Integer port;
    private String password;

    @PostConstruct
    public void postConstruct() {
        System.out.println("PostConstruct " + this.host + ":" + this.port);
    }
}