package com.clubeevantagens.authmicroservice.controller;
import com.clubeevantagens.authmicroservice.model.Client;
import com.clubeevantagens.authmicroservice.model.ClientDTO;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    // CREATE
    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody ClientDTO clientDTO) {
        return clientService.registerClient(clientDTO);
    }


    // READ
    @GetMapping
    public ResponseEntity<List<Client>> getAllClient() {
        return clientService.getAllClients();
    }


    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<String> updateClient(@PathVariable Long id, @RequestBody ClientDTO newClientDTO) {
       return clientService.updateClient(id,newClientDTO);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        return clientService.deleteClient(id);
    }




}
