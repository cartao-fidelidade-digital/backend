package com.clubeevantagens.authmicroservice.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CompanyGetReturn {
    private String email;
    private String companyName;
    private String cpf;
    private String cnpj;
    private String type;
    private String contactPhone;
}
