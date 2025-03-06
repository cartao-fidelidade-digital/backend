package com.clubeevantagens.authmicroservice.application.usecase;

import com.clubeevantagens.authmicroservice.model.dto.ClientDto;
import com.clubeevantagens.authmicroservice.repository.ClientRepository;
import com.clubeevantagens.authmicroservice.repository.UserRepository;
import com.clubeevantagens.authmicroservice.service.ClientService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    ClientService clientService;

    @Mock
    ClientRepository clientRepository;

    @Mock
    UserRepository userRepository;

    @Test
    @DisplayName("Deve retornar erro caso email ja exista ao criar cliente")
    void shouldReturnErrorWhenEmailAlreadyExistsInRegisterClient() {

        // Usuario
        ClientDto clientDto = new ClientDto();
        clientDto.setEmail("teste@gmail.com");
        clientDto.setPassword("sfwofngDFGos1224@#");

        // Simulaçao de resultado
        Mockito.when(userRepository.existsByEmail(clientDto.getEmail())).thenReturn(true);

        // Teste
        ResponseEntity<?> response = clientService.registerClient(clientDto);

        // validação do teste
        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email já cadastrado"),response);
    }


    @Test
    @DisplayName("Deve retornar erro caso CPF seja inválido ao criar cliente")
    void shouldReturnErrorWhenCPFIsInvalidInRegisterClient() {
        // Usuario
        ClientDto clientDto = new ClientDto();
        clientDto.setEmail("teste@gmail.com");
        clientDto.setPassword("sfwofngDFGos1224@#");
        clientDto.setCpf("987.987.879-78");

        // Teste
        ResponseEntity<?> response = clientService.registerClient(clientDto);

        // validação do teste
        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido"),response);
    }

    @Test
    @DisplayName("Deve retornar erro caso Email seja inválido ao criar cliente")
    void shouldReturnErrorWhenEmailIsInvalidInRegisterClient(){
        // Usuario
        ClientDto clientDto = new ClientDto();
        clientDto.setEmail("testegmailcom");
        clientDto.setPassword("sfwofngDFGos1224@#");
        clientDto.setCpf("808.425.610-64");

        // Teste
        ResponseEntity<?> response = clientService.registerClient(clientDto);

        // validação do teste
        assertEquals(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email inválido"),response);
    }



}