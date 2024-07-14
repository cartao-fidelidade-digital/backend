package com.clubeevantagens.authmicroservice.document;

import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.model.dto.LoginReturn;
import com.clubeevantagens.authmicroservice.model.dto.NewTokenDto;
import com.clubeevantagens.authmicroservice.model.dto.NewTokenReturn;
import com.clubeevantagens.authmicroservice.model.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public interface UserDocs {

    // LOGAR
    @Operation(summary = "Logar Usuario")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "usuario logado com sucesso",
                    content = {@Content(schema = @Schema(implementation = LoginReturn.class),mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "email ou senha incorreta", content = @Content),
            @ApiResponse(responseCode = "500", description = "ocorreu um erro inesperado", content = @Content)
    })
    ResponseEntity<?> logar(UserDto user);


    // NEW TOKEN
    @Operation(summary = "Novo Token")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "token criado com sucesso",
                    content = {@Content(schema = @Schema(implementation = NewTokenReturn.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "access-token ou refresh-token invalido", content = @Content),

    })
    public ResponseEntity<?> newToken(@RequestBody NewTokenDto newToken);

}
