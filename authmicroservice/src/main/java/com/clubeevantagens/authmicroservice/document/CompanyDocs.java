package com.clubeevantagens.authmicroservice.document;

import com.clubeevantagens.authmicroservice.model.dto.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface CompanyDocs {
    // CREATE
    @Operation(summary = "Criar Empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "empresa criada com sucesso", content = @Content),
            @ApiResponse(responseCode = "400", description = "email já cadastrado " +
                    "<br> email inválido " +
                    "<br> CPF inválido" +
                    "<br> CNPJ inválido" +
                    "<br>sua senha precisa conter 8 a 20 caracteres incluindo números, letras maiúsculas e minúsculas e caracteres especiais.", content = @Content),
            @ApiResponse(responseCode = "403", description = "usuario não autorizado", content = @Content),})
    public ResponseEntity<?> registerCompany(@RequestBody CompanyDto companyDTO);

    // READ



    // UPDATE
    @Operation(summary = "Editar Empresa")
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
    @Operation(summary = "Deletar Empresa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "empresa deletada com sucesso", content = @Content),
            @ApiResponse(responseCode = "403", description = "usuario não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "empresa não encontrada", content = @Content),})
    public ResponseEntity<?> deleteCompany(
            @Parameter(in = ParameterIn.HEADER, name = "Authorization", description = "Access Token", required = true)
            @RequestHeader("Authorization") String authorization);

    // GET-COMPANY
    @Operation(summary = "Resgatar Empresa")
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
