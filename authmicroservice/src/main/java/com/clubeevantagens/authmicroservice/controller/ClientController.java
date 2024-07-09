package com.clubeevantagens.authmicroservice.controller;
import com.clubeevantagens.authmicroservice.model.Client;
import com.clubeevantagens.authmicroservice.model.ClientDTO;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.security.JwtUtils;
import com.clubeevantagens.authmicroservice.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private JwtUtils jwtUtils;

    // CREATE
    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody ClientDTO clientDTO) {
        return clientService.registerClient(clientDTO);
    }


    // READ
    @GetMapping("/all")
    public ResponseEntity<?> getAllClient() {
        return clientService.getAllClients();
    }


    // UPDATE
    @PutMapping
    public ResponseEntity<String> updateClient(@RequestHeader Map<String,String> header, @RequestBody ClientDTO newClientDTO) {
        var accessToken = header.get("authorization").substring(7);
        var accessTokenMap = jwtUtils.extractAccessToken(accessToken);
        var id = accessTokenMap.get("sub");
        return clientService.updateClient(Long.valueOf(id),newClientDTO);
    }

    // DELETE
    @DeleteMapping
    public ResponseEntity<?> deleteClient(@RequestHeader Map<String,String> header) {
        var accessToken = header.get("authorization").substring(7);
        var accessTokenMap = jwtUtils.extractAccessToken(accessToken);
        var id = accessTokenMap.get("sub");
        return clientService.deleteClient(Long.valueOf(id));
    }

    // GET-CLIENT
    @GetMapping
    public ResponseEntity<?> getClient(@RequestHeader Map<String,String> header) {
        var accessToken = header.get("authorization").substring(7);
        var accessTokenMap = jwtUtils.extractAccessToken(accessToken);
        var id = accessTokenMap.get("sub");
        return clientService.getClient(Long.valueOf(id));
    }


}
