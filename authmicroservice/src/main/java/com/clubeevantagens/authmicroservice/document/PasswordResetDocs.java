package com.clubeevantagens.authmicroservice.document;

import com.clubeevantagens.authmicroservice.model.dto.ForgotDto;
import com.clubeevantagens.authmicroservice.model.dto.LoginReturn;
import com.clubeevantagens.authmicroservice.model.dto.ResetDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface PasswordResetDocs {
    // ESQUECI SENHA
    @Operation(summary = "Esqueci Senha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "solicitação de redefinição realizada. Voce recebera um email em alguns minutos", content = @Content),
            @ApiResponse(responseCode = "404", description = "email não encontrado", content = @Content)
    })
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotDto payload);


    // RESETA SENHA
    @Operation(summary = "Resetar Senha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "senha alterada com sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "token invalido", content = @Content),
    })
    public ResponseEntity<?> resetPassword(@RequestBody ResetDto payload);
}
