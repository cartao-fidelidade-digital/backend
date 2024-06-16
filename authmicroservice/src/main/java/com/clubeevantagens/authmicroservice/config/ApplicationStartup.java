package com.clubeevantagens.authmicroservice.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup {

    @EventListener(ApplicationReadyEvent.class)
    public void insertDataOnStartup() { // Realiza alguma ação ao iniciar o Spring

    }

}
