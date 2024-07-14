package com.clubeevantagens.authmicroservice.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ResetDto {
    private String token;
    private String newPassword;
}
