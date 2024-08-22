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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface CompanyDocs {
    // CREATE
    @Operation(summary = "Criar Empresa", description = "Endpoint para criar uma nova empresa.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "202",
                    description = "usuario logado com sucesso",
                    content = {@Content(schema = @Schema(implementation = LoginReturn.class),mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "email já cadastrado " +
                    "<br> email inválido " +
                    "<br> CPF inválido" +
                    "<br> CNPJ inválido" +
                    "<br>sua senha precisa conter 8 a 20 caracteres incluindo números, letras maiúsculas e minúsculas e caracteres especiais.", content = @Content),
            @ApiResponse(responseCode = "403", description = "usuario não autorizado", content = @Content),})
    public ResponseEntity<?> registerCompany(@RequestBody CompanyDto companyDTO);

    // READ
    @Operation(summary = "Resgatar Todas as Empresas", description = "Endpoint para resgatar todas as empresas. <br><br>Role: Admin", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "empresas resgatadas com sucesso",
                    content = {@Content(schema = @Schema(implementation = ClientGetReturn.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", description = "usuario não autorizado", content = @Content),})
    public ResponseEntity<?> getAllCompanies(
            @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Access Token", required = true)
            @RequestHeader("Authorization") String authorization
    );


    // UPDATE
    @Operation(summary = "Editar Empresa", description = "Endpoint para editar uma empresa específica. <br><br>Role: Company", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "empresa editada com sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "CPF inválido <br> CNPJ inválido", content = @Content),
            @ApiResponse(responseCode = "403", description = "usuario não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "empresa não encontrada", content = @Content),})
    public ResponseEntity<?> updateCompany(
            @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Token JWT", required = true)
            @RequestHeader("Authorization") String authorization,
            @RequestBody CompanyUpdateDto newCompanyDto);


    // DELETE
    @Operation(summary = "Deletar Empresa", description = "Endpoint para excluir uma empresa específica. <br><br>Role: Company", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "empresa deletada com sucesso", content = @Content),
            @ApiResponse(responseCode = "403", description = "usuario não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "empresa não encontrada", content = @Content),})
    public ResponseEntity<?> deleteCompany(
            @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Access Token", required = true)
            @RequestHeader("Authorization") String authorization);

    // GET-COMPANY
    @Operation(summary = "Resgatar Empresa", description = "Endpoint para obter as informações de uma empresa específica. <br><br>Role: Company", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "empresa resgatada com sucesso",
                    content = {@Content(schema = @Schema(implementation = CompanyGetReturn.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "403", description = "usuario não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "empresa não encontrada", content = @Content),})
    public ResponseEntity<?> getCompany(
            @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Access Token", required = true)
            @RequestHeader("Authorization") String authorization);
}
