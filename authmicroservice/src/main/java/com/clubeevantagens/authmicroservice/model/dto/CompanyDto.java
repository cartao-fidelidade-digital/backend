package com.clubeevantagens.authmicroservice.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CompanyDto {

    private String email;
    private String password;

    private String companyName;
    private String cpf;
    private String cnpj;
    private String contactPhone;
    private String type;
    private boolean termsOfUse;



}
