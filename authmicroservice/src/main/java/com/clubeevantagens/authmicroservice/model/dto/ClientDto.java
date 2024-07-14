package com.clubeevantagens.authmicroservice.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClientDto {

    private String email;
    private String password;

    private String name;
    private String cpf;
    private String phoneNumber;
    private boolean termsOfUse;



}
