package com.clubeevantagens.authmicroservice.controller;
import com.clubeevantagens.authmicroservice.model.ClientDTO;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.repository.UserRepository;
import com.clubeevantagens.authmicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> registerClient(@RequestBody User user) {
        return userService.loginUser(user);
    }

    @PostMapping("/newtoken")
    public ResponseEntity<?> newToken(@RequestBody Map<String, String> payload) {
        String expiredAccessToken = payload.get("expiredAccessToken");
        String refreshToken = payload.get("refreshToken");
        return userService.newToken(expiredAccessToken,refreshToken);
    }

}
