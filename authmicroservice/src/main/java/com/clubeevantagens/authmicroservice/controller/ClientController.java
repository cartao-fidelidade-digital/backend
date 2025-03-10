package com.clubeevantagens.authmicroservice.controller;
import com.clubeevantagens.authmicroservice.document.ClientDocs;
import com.clubeevantagens.authmicroservice.model.Client;
import com.clubeevantagens.authmicroservice.model.dto.ClientDto;
import com.clubeevantagens.authmicroservice.model.dto.ClientUpdateDto;
import com.clubeevantagens.authmicroservice.model.dto.ReviewDto;
import com.clubeevantagens.authmicroservice.security.JwtUtils;
import com.clubeevantagens.authmicroservice.service.ClientService;
import com.clubeevantagens.authmicroservice.service.ReviewsService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users/client")
public class ClientController implements ClientDocs {
    @Autowired
    private ClientService clientService;

    @Autowired
    private ReviewsService reviewsService;

    @Autowired
    private JwtUtils jwtUtils;

    // CREATE
    @PostMapping("/register")
    @Override
    public ResponseEntity<?> registerClient(@RequestBody ClientDto clientDTO) {
        return clientService.registerClient(clientDTO);
    }


    // READ
    @GetMapping("/all")
    @Override
    public ResponseEntity<?> getAllClient(@RequestHeader("Authorization") String authorization) {
        return clientService.getAllClients();
    }


    // UPDATE
    @PutMapping
    @Override
    public ResponseEntity<?> updateClient(@RequestHeader("Authorization") String authorization, @RequestBody ClientUpdateDto newClientDto) {

        var accessToken = authorization.substring(7);
        var accessTokenMap = jwtUtils.extractAccessToken(accessToken);
        var id = accessTokenMap.get("sub");
        return clientService.updateClient(Long.valueOf(id), newClientDto);
    }

    // DELETE
    @DeleteMapping
    @Override
    public ResponseEntity<?> deleteClient(@RequestHeader("Authorization") String authorization) {
        var accessToken = authorization.substring(7);
        var accessTokenMap = jwtUtils.extractAccessToken(accessToken);
        var id = accessTokenMap.get("sub");
        return clientService.deleteClient(Long.valueOf(id));
    }

    // GET-CLIENT
    @GetMapping
    @Override
    public ResponseEntity<?> getClient(@RequestHeader("Authorization") String authorization) {
        var accessToken = authorization.substring(7);
        var accessTokenMap = jwtUtils.extractAccessToken(accessToken);
        var id = accessTokenMap.get("sub");
        return clientService.getClient(Long.valueOf(id));
    }

    // CRIE AVALIAÇÃO
    @PostMapping("/review")
    public ResponseEntity<?> registerReview(@RequestHeader("Authorization") String authorization, @RequestBody ReviewDto reviewDto) {
        var accessToken = authorization.substring(7);
        var accessTokenMap = jwtUtils.extractAccessToken(accessToken);
        var id = accessTokenMap.get("sub");

        reviewDto.setClient(Long.valueOf(id));
        return reviewsService.registerReview(reviewDto);
    }
}
