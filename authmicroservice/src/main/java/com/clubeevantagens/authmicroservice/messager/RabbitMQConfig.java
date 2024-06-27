package com.clubeevantagens.authmicroservice.messager;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    // FILA EMAIL
    public Queue emailQueue(@Value("${email.queue.name}") String emailQueueName) {
        return new Queue(emailQueueName, true); // Cria a fila de email
    }

    @Bean
    // EXCHAGE EMAIL
    public DirectExchange emailExchange(@Value("${email.exchange.name}") String emailExchangeName) {
        return new DirectExchange(emailExchangeName); // Cria a exchange de email
    }

    @Bean
    // BINDING EMAIL
    public Binding emailBinding(Queue emailQueue, DirectExchange emailExchange, @Value("${email.routing.key}") String emailRoutingKey) {
        return BindingBuilder.bind(emailQueue).to(emailExchange).with(emailRoutingKey); // Vincula a fila à exchange usando a chave de roteamento de email
    }
}
