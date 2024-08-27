package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.*;
import com.clubeevantagens.authmicroservice.model.dto.ClientDto;
import com.clubeevantagens.authmicroservice.model.dto.ClientUpdateDto;
import com.clubeevantagens.authmicroservice.model.dto.UserDto;
import com.clubeevantagens.authmicroservice.repository.ClientRepository;
import com.clubeevantagens.authmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class ClientService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // CRIAR CLIENTE
    public ResponseEntity<?> registerClient(ClientDto clientDTO){

        // Verifica se o email já está cadastrado
        if (userRepository.findUserByEmail(clientDTO.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email já cadastrado");
        }

        // Valida CPF se não for nulo
        if (clientDTO.getCpf() != null && !isValidCPF(clientDTO.getCpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido");
        }


        User user = new User();

        // Valida Email
        if(!user.isValidEmail(clientDTO.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email inválido");
        }
        // Valida Senha
        if(!user.isValidPassword(clientDTO.getPassword()) ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sua senha precisa conter 8 a 20 caracteres incluindo números, letras maiúsculas e minúsculas e caracteres especiais.");
        }
        user.setEmail(clientDTO.getEmail());
        user.setPassword(user.encodePassword(clientDTO.getPassword()));
        user.setActive(true);

        List<Role> roles = new ArrayList<>();
        roles.add(new Role(1L,"CLIENT",null));

        // add roles
        user.setRoles(roles);

        // Salva "User" no banco
        userRepository.save(user);

        Client client = new Client();
        client.setUser(user);
        client.setName(clientDTO.getName());
        client.setCpf(clientDTO.getCpf());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setTermsOfUse(clientDTO.isTermsOfUse());
        client.setDateTermsOfUse(client.dateNow());
        client.setSocialName(clientDTO.getSocialName());
        client.setPreferences(clientDTO.getPreferences());

        // Salva "Client" no banco
        clientRepository.save(client);

        // Realizar Login
        var retorno = userService.loginUser(new UserDto(clientDTO.getEmail(),clientDTO.getPassword()));

        return retorno;
    }



    // BUSCAR TODOS OS CLIENTES
    public ResponseEntity<?> getAllClients() {

        List<Client> clients = clientRepository.findAll();
        List<ClientDto> retorno = new ArrayList<>();

        for(Client c : clients){
            ClientDto cAux = new ClientDto();
            cAux.setName(c.getName());
            cAux.setPassword(c.getUser().getPassword());
            cAux.setEmail(c.getUser().getEmail());
            cAux.setCpf(c.getCpf());
            cAux.setPhoneNumber(c.getPhoneNumber());
            cAux.setTermsOfUse(c.isTermsOfUse());
            cAux.setSocialName(c.getSocialName());
            cAux.setPreferences(c.getPreferences());

            retorno.add(cAux);
        }


        return ResponseEntity.ok(retorno);
    }



    // EDITAR CLIENTE
    public ResponseEntity<String> updateClient(Long idUser, ClientUpdateDto newClientDto) {

        // Valida CPF se não for nulo
        if (newClientDto.getCpf() != null && !isValidCPF(newClientDto.getCpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido");
        }

        // Verifica se "user" existe
        var userOptional = userRepository.findById(idUser);
        if(userOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        // Verifica se "client" exite
        var clientOptional = clientRepository.findClientByUser(userOptional.get());
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            Optional.ofNullable(newClientDto.getName()).ifPresent(client::setName);
            Optional.ofNullable(newClientDto.getCpf()).ifPresent(client::setCpf);
            Optional.ofNullable(newClientDto.getPhoneNumber()).ifPresent(client::setPhoneNumber);
            Optional.ofNullable(newClientDto.getSocialName()).ifPresent(client::setSocialName);
            Optional.ofNullable(newClientDto.getPreferences()).ifPresent(client::setPreferences);

            clientRepository.save(client);// salva "client"

            return ResponseEntity.status(HttpStatus.CREATED).body("Cliente editado com sucesso");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    // DELETAR CLIENTE
    public ResponseEntity<?> deleteClient(Long idUser) {
        var userOptional = userRepository.findById(idUser);
        if (userOptional.isPresent()) {
            userService.deleteUser(idUser);// deleta "user"
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET-CLIENT
    public ResponseEntity<?> getClient(Long idUser){
        var userOptional = userRepository.findById(idUser);
        if (userOptional.isPresent()) {
            var client = clientRepository.findClientByUser(userOptional.get()).get();
            var payload = new HashMap<>();
            payload.put("email",client.getUser().getEmail());
            payload.put("name",client.getName());
            payload.put("cpf",client.getCpf());
            payload.put("phoneNumber",client.getPhoneNumber());
            payload.put("socialName",client.getSocialName());
            payload.put("preferences", client.getPreferences());
            return ResponseEntity.ok(payload);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Remove Formatação de CPF ou CNPJ
    public String removeFormatting(String value) {
        return value.replaceAll("[^\\d]", ""); // Remove todos os caracteres que não são dígitos
    }


    // Validador de CPF
    public boolean isValidCPF(String cpf) {

        cpf = removeFormatting(cpf); // Remove formatação

        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int firstCheckDigit = 11 - (sum % 11);
            if (firstCheckDigit > 9) firstCheckDigit = 0;
            if (firstCheckDigit != Character.getNumericValue(cpf.charAt(9))) return false;

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int secondCheckDigit = 11 - (sum % 11);
            if (secondCheckDigit > 9) secondCheckDigit = 0;
            return secondCheckDigit == Character.getNumericValue(cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }





}
