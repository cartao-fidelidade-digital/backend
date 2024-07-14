package com.clubeevantagens.authmicroservice.model.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CompanyUpdateDto {
    private String companyName;
    private String cpf;
    private String cnpj;
    private String contactPhone;
    private String type;
}
