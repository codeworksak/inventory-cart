package com.codeworks.inventorycart.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    @Value("${app.rabbitmq.queue-name}")
    public static final String QUEUE_NAME="";
    @Value("${app.rabbitmq.exchange-name}")
    public static final String EXCHANGE_NAME="";
    @Value("${app.rabbitmq.routing-key}")
    public static final String  ROUTING_KEY="";

    @Autowired
    private CustomRabbitProperties customRabbitProperties;
//    @Value("${RABBIT_HOST:localhost}")
//    private String rabbitHost;
//    @Value("${RABBIT_PORT:5672}")
//    private Integer rabbitPort;
//    @Value("${RABBIT_USER:guest}")
//    private String rabbitUser;
//    @Value("${RABBIT_PASS:guest}")
//    private String rabbitPassword;

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(customRabbitProperties.getHost());
        connectionFactory.setPort(customRabbitProperties.getPort());
        connectionFactory.setUsername(customRabbitProperties.getUsername());
        connectionFactory.setPassword(customRabbitProperties.getPassword());
        System.out.println(connectionFactory.getClass() + "  " + connectionFactory.getHost() + "  " + connectionFactory.getPort());
        return connectionFactory;
    }

    @Bean
    public Queue queue()
    {
        return new org.springframework.amqp.core.Queue(customRabbitProperties.getQueueName1(),true);
    }

    @Bean
    public Queue queueOrder()
    {
        return new org.springframework.amqp.core.Queue(customRabbitProperties.getQueueOrder(),true);
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

//    @Bean
//    public TopicExchange exchange()
//    {
//        return new TopicExchange(customRabbitProperties.getExchangeName());
//    }
    @Bean
    public  FanoutExchange exchange()
    {
        return new FanoutExchange(customRabbitProperties.getExchangeName());
    }
    @Bean
    public Binding binding(Queue queue,FanoutExchange excg)
    {
        return BindingBuilder.bind(queue).to(excg);//.with(ROUTING_KEY)
                //.with(customRabbitProperties.getRoutingKey());
    }
    @Bean
    public Binding bindingOrder(Queue queueOrder,FanoutExchange excg)
    {
        return BindingBuilder.bind(queueOrder).to(excg);//.with(ROUTING_KEY)
        //.with(customRabbitProperties.getRoutingKey());
    }

}
