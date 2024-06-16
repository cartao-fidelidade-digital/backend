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
public class CompanyDTO {

    private String email;
    private String password;

    private String companyName;
    private String cpf;
    private String cnpj;
    private String contactPhone;
    private String type;
    private boolean termsOfUse;
    private String dateTermsOfUse;

}
