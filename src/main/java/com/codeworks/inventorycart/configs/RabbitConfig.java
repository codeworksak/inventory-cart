package com.codeworks.inventorycart.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String QUEUE_NAME="order-q";
    public static final String EXCHANGE_NAME="ordering";
    public static final String  ROUTING_KEY="order-routing";

    @Bean
    public Queue queue()
    {
        return new org.springframework.amqp.core.Queue(QUEUE_NAME,true);
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabTemp=new RabbitTemplate(connectionFactory);
        rabTemp.setMessageConverter(jsonMessageConverter());
        return rabTemp;
    }

    @Bean
    public MessageConverter jsonMessageConverter()
    {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public TopicExchange exchange()
    {
        return new TopicExchange(EXCHANGE_NAME);
    }
    @Bean
    public Binding binding(Queue q,TopicExchange excg)
    {
        return BindingBuilder.bind(q)
                .to(excg)
                .with(ROUTING_KEY);
    }
}
