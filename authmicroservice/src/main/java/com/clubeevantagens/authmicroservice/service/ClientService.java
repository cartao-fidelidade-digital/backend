package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.Client;
import com.clubeevantagens.authmicroservice.model.ClientDTO;
import com.clubeevantagens.authmicroservice.model.Company;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.repository.ClientRepository;
import com.clubeevantagens.authmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class ClientService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserService userService;

    // CRIAR CLIENTE
    public ResponseEntity<String> registerClient(ClientDTO clientDTO){

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
        user.setPassword(clientDTO.getPassword());
        // Salva "User" no banco
        userRepository.save(user);

        Client client = new Client();
        client.setUser(user);
        client.setName(clientDTO.getName());
        client.setCpf(clientDTO.getCpf());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setTermsOfUse(clientDTO.isTermsOfUse());
        client.setDateTermsOfUse(client.dateNow());

        // Salva "Client" no banco
        clientRepository.save(client);


        return ResponseEntity.status(HttpStatus.CREATED).body("Cliente salvo com sucesso");
    }



    // BUSCAR TODOS OS CLIENTES
    public ResponseEntity<?> getAllClients() {

        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> retorno = new ArrayList<>();

        for(Client c : clients){
            ClientDTO cAux = new ClientDTO();
            cAux.setName(c.getName());
            cAux.setPassword(c.getUser().getPassword());
            cAux.setEmail(c.getUser().getEmail());
            cAux.setCpf(c.getCpf());
            cAux.setPhoneNumber(c.getPhoneNumber());
            cAux.setTermsOfUse(c.isTermsOfUse());
            cAux.setDateTermsOfUse(c.getDateTermsOfUse().toString());

            retorno.add(cAux);
        }


        return ResponseEntity.ok(retorno);
    }



    // EDITAR CLIENTE
    public ResponseEntity<String> updateClient(@PathVariable Long id, @RequestBody ClientDTO newClientDTO) {

        // Valida CPF se não for nulo
        if (newClientDTO.getCpf() != null && !isValidCPF(newClientDTO.getCpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido");
        }

        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            client.setName(newClientDTO.getName());
            client.setCpf(newClientDTO.getCpf());
            client.setPhoneNumber(newClientDTO.getPhoneNumber());

            clientRepository.save(client);

            return ResponseEntity.status(HttpStatus.CREATED).body("Cliente editado com sucesso");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    // DELETAR CLIENTE
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (clientOptional.isPresent()) {
            clientRepository.deleteById(id);

            Client client = clientOptional.get();
            userService.deleteUser(client.getUser().getId());

            return ResponseEntity.ok().build();
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
