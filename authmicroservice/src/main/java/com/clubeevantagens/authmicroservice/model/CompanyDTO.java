package com.clubeevantagens.authmicroservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
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

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Date dateTermsOfUse;

}
