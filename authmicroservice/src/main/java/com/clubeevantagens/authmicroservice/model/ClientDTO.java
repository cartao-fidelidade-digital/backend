package com.clubeevantagens.authmicroservice.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClientDTO {

    private String email;
    private String password;

    private String name;
    private String cpf;
    private String phoneNumber;
    private boolean termsOfUse;
    private String dateTermsOfUse;

}
