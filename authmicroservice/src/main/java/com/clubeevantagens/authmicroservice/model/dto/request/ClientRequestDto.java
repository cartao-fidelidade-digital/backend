package com.clubeevantagens.authmicroservice.model.dto.request;

import com.clubeevantagens.authmicroservice.validator.validCategories.ValidCategories;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.Set;

@Schema(name = "CreateClientRequest", description = "DTO para cadastro de novo cliente")
public record ClientRequestDto(
        @Schema(description = "E-mail válido do cliente",
                example = "cliente@example.com",
                format = "email",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @Email @NotBlank String email,

        @Schema(description = "Senha segura (8-20 caracteres, 1 maiúscula, 1 número, 1 especial)",
                example = "Senha@123",
                pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @Pattern(regexp = "...") String password,

        @Schema(description = "Nome completo do cliente",
                example = "João da Silva",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String name,

        @Schema(description = "CPF válido",
                example = "123.456.789-09",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @CPF @NotBlank String cpf,

        @Schema(description = "Telefone no formato (99)99999-9999",
                example = "(11)91234-5678",
                pattern = "^\\(\\d{2}\\)\\d{5}-\\d{4}$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Pattern(regexp = "...") String phoneNumber,

        @Schema(description = "Aceite dos termos de uso",
                example = "true",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotNull @AssertTrue Boolean termsOfUse,

        @Schema(description = "Nome social (opcional)",
                example = "Joana Silva")
        @Nullable String socialName,

        @Schema(description = "Categorias de interesse (mínimo 3 ou nenhuma)",
                example = "Alimentação",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @ValidCategories
        Set<String> preferences,

        @Schema(description = "Data de nascimento (dd/MM/yyyy)",
                example = "15/05/1990",
                format = "dd/MM/yyyy")
        @JsonFormat(pattern = "dd/MM/yyyy") @Past LocalDate birthDate,

        @Schema(description = "URL da foto do perfil",
                example = "https://example.com/photo.jpg")
        String photo,

        @Schema(description = "CEP no formato 99999-999",
                example = "01311-000",
                pattern = "^\\d{5}-\\d{3}$",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank @Pattern(regexp = "...") String cep,

        @Schema(description = "Número do endereço",
                example = "150",
                minimum = "1",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @Positive Integer addressNumber,

        @Schema(description = "Complemento do endereço",
                example = "Apto 42",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @NotBlank String complement
){}