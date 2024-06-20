package com.clubeevantagens.authmicroservice.controller;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController {

    @Autowired
    private PasswordResetService passwordResetService;

    // ESQUECI SENHA
    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> payload) {
        String email = payload.get("email");
        return passwordResetService.sendPasswordResetTokenToEmail(email);
    }

    // RESETA SENHA
    @PostMapping("/reset")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        String newPassword = payload.get("newPassword");
        return passwordResetService.resetPassword(token, newPassword);
    }
}
