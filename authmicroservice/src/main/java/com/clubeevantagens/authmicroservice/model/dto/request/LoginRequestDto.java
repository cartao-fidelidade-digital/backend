package com.clubeevantagens.authmicroservice.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(
        @Email
        @NotBlank
        String email,

        @NotBlank
        String password
) {
}
