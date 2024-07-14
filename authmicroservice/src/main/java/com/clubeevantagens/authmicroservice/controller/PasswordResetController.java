package com.clubeevantagens.authmicroservice.controller;
import com.clubeevantagens.authmicroservice.document.PasswordResetDocs;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.model.dto.ForgotDto;
import com.clubeevantagens.authmicroservice.model.dto.ResetDto;
import com.clubeevantagens.authmicroservice.service.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/password")
public class PasswordResetController implements PasswordResetDocs {

    @Autowired
    private PasswordResetService passwordResetService;

    // ESQUECI SENHA
    @PostMapping("/forgot")
    @Override
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotDto payload) {
        return passwordResetService.sendPasswordResetTokenToEmail(payload.getEmail());
    }

    // RESETA SENHA
    @PostMapping("/reset")
    @Override
    public ResponseEntity<?> resetPassword(@RequestBody ResetDto payload) {
        return passwordResetService.resetPassword(payload.getToken(), payload.getNewPassword());
    }
}
