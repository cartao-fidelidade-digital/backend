package com.clubeevantagens.authmicroservice.document;

import com.clubeevantagens.authmicroservice.model.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

public interface ClientDocs {

    // CREATE
    @Operation(summary = "Criar Cliente" , description = "Endpoint para criar um novo cliente." )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "usuario logado com sucesso",
                    content = {@Content(schema = @Schema(implementation = LoginReturn.class),mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "email já cadastrado " +
                    "<br> email inválido " +
                    "<br> CPF inválido" +
                    "<br>sua senha precisa conter 8 a 20 caracteres incluindo números, letras maiúsculas e minúsculas e caracteres especiais.", content = @Content),
            @ApiResponse(responseCode = "403", description = "usuario não autorizado", content = @Content),})
    public ResponseEntity<?> registerClient(@RequestBody ClientDto clientDTO);

    // READ
    @Operation(summary = "Resgatar Todos os Clientes", description = "Endpoint para resgatar todos os clientes. <br><br>Role: Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "clientes resgatados com sucesso",
                    content = {@Content(schema = @Schema(implementation = ClientGetReturn.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", description = "usuario não autorizado", content = @Content),})
    public ResponseEntity<?> getAllClient(
            @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Access Token", required = true)
            @RequestHeader("Authorization") String authorization
    );


    // UPDATE
    @Operation(summary = "Editar Cliente", description = "Endpoint para editar um cliente específico. <br><br>Role: Client", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "cliente editado com sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "CPF inválido", content = @Content),
            @ApiResponse(responseCode = "403", description = "usuario não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "cliente não encontrado", content = @Content),})
    public ResponseEntity<?> updateClient(
            @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Token JWT", required = true)
            @RequestHeader("Authorization") String authorization,
            @RequestBody ClientUpdateDto newClientDto);


    // DELETE
    @Operation(summary = "Deletar Cliente", description = "Endpoint para excluir um cliente específico. <br><br>Role: Client", security = @SecurityRequirement(name = "bearerAuth"))

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "cliente deletado com sucesso", content = @Content),
            @ApiResponse(responseCode = "403", description = "usuario não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "cliente não encontrado", content = @Content),})
    public ResponseEntity<?> deleteClient(
            @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Access Token", required = true)
            @RequestHeader("Authorization") String authorization);

    // GET-CLIENT
    @Operation(summary = "Resgatar Cliente", description = "Endpoint para obter as informações de um cliente específico. <br><br>Role: Client", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "cliente resgatado com sucesso",
                    content = {@Content(schema = @Schema(implementation = ClientGetReturn.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", description = "usuario não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "cliente não encontrado", content = @Content),})
    public ResponseEntity<?> getClient(
            @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Access Token", required = true)
            @RequestHeader("Authorization") String authorization);
}
