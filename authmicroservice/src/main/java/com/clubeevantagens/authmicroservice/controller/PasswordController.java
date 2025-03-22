package com.clubeevantagens.authmicroservice.controller;

import com.clubeevantagens.authmicroservice.exception.ErrorMessage;
import com.clubeevantagens.authmicroservice.model.data.User;
import com.clubeevantagens.authmicroservice.model.dto.request.ForgotRequestDto;
import com.clubeevantagens.authmicroservice.model.dto.request.ResetPasswordRequestDto;
import com.clubeevantagens.authmicroservice.model.dto.request.UpdatePasswordRequestDto;
import com.clubeevantagens.authmicroservice.service.PasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/passwords")
@Tag(name = "Password Management", description = "Endpoints para gerenciamento de senhas e recuperação de conta")
public class PasswordController {

    private final PasswordService passwordService;

    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Operation(summary = "Solicitar recuperação de senha",
            description = "Envia um token de recuperação para o e-mail do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Token enviado com sucesso",
                    content = @Content(schema = @Schema(implementation = String.class,
                            example = "Password reset request successfully processed. You'll receive an email within a few minutes"))),
            @ApiResponse(responseCode = "404", description = "E-mail não encontrado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping("/forgot")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(passwordService.sendPasswordResetTokenToEmail(dto.email()));
    }

    @Operation(summary = "Redefinir senha com token",
            description = "Permite definir uma nova senha usando o token de recuperação")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso",
                    content = @Content(schema = @Schema(implementation = String.class,
                            example = "Password successfully updated."))),
            @ApiResponse(responseCode = "400", description = "Token inválido ou expirado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPasswordRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(passwordService.resetPassword(dto.resetPasswordToken().getToken(), dto.password()));
    }

    @Operation(summary = "Atualizar senha atual",
            description = "Permite ao usuário autenticado alterar sua senha usando a senha antiga")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = String.class,
                            example = "Password successfully updated."))),
            @ApiResponse(responseCode = "400", description = "Senha antiga incorreta",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class))),
            @ApiResponse(responseCode = "401", description = "Não autenticado",
                    content = @Content(schema = @Schema(implementation = ErrorMessage.class)))
    })
    @PostMapping("/update/me")
    public ResponseEntity<String> updatePassword(@RequestBody @Valid UpdatePasswordRequestDto dto, Authentication authentication) {
        User userDetails = (User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(passwordService.updatePassword(userDetails.getId(), dto.oldPassword(), dto.newPassword()));
    }
}