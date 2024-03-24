package com.javastart.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// лучше создавать конфиги скриптами
@EnableRabbit
@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_TRANSPORTATION = "js.trans.notify";
    private static final String TOPIC_EXCHANGE_TRANSPORTATION = "js.trans.notify.exchange";
    private static final String ROUTING_KEY_TRANSPORTATION = "js.key.transportation";

    // переменная для создания очередей и exchange и управления с ними


    @Bean
    public TopicExchange transportationExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_TRANSPORTATION);
    }

    @Bean
    public Queue queueTransportation() {
        return new Queue(QUEUE_TRANSPORTATION);
    }

    @Bean
    public Binding transportationBinding(){
        return BindingBuilder.bind(queueTransportation())
                .to(transportationExchange())
                .with(ROUTING_KEY_TRANSPORTATION);
    }


}
