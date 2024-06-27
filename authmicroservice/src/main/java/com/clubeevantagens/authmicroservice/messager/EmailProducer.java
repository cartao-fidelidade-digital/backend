package com.clubeevantagens.authmicroservice.messager;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${email.exchange.name}")
    private String emailExchangeName;

    @Value("${email.routing.key}")
    private String emailRoutingKey;

    public void sendEmailMessage(String to, String subject, String text) {
        String message = to + ";" + subject + ";" + text;
        rabbitTemplate.convertAndSend(emailExchangeName, emailRoutingKey, message); // Envia a mensagem
    }
}
