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
public class UserDTO {
    private String name;
    private String email;
    private String password;
    private boolean termsOfUse;
    private String dateTermsOfUse;
    private String cpf;
    private String cnpj;
    private List<Long> roles; // Lista de IDs de roles

}
