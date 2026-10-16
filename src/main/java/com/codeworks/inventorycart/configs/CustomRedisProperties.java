package com.codeworks.inventorycart.configs;

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

}
