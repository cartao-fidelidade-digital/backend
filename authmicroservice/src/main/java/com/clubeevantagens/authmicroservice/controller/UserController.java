package com.clubeevantagens.authmicroservice.controller;
import com.clubeevantagens.authmicroservice.document.UserDocs;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.model.dto.NewTokenDto;
import com.clubeevantagens.authmicroservice.model.dto.UserDto;
import com.clubeevantagens.authmicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController implements UserDocs {
    @Autowired
    private UserService userService;

    // LOGIN
    @PostMapping("/login")
    @Override
    public ResponseEntity<?> logar(@RequestBody UserDto user) {
        return userService.loginUser(user);
    }

    // NEW TOKEN
    @PostMapping("/newtoken")
    @Override
    public ResponseEntity<?> newToken(@RequestBody NewTokenDto payload) {
        return userService.newToken(payload.getExpiredAccessToken(),payload.getRefreshToken());
    }

}
