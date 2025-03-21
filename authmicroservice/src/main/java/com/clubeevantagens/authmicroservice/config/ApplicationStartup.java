package com.clubeevantagens.authmicroservice.config;

import com.clubeevantagens.authmicroservice.model.Role;
import com.clubeevantagens.authmicroservice.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup {

    @Autowired
    private RoleRepository roleRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void insertDataOnStartup() { // Realiza alguma ação ao iniciar o Spring
        Role client = new Role(1L,"CLIENT",null);
        Role company = new Role(2L,"COMPANY",null);
        Role admin = new Role(3L,"ADMIN",null);

        roleRepository.save(admin);
        roleRepository.save(client);
        roleRepository.save(company);
    }

}
