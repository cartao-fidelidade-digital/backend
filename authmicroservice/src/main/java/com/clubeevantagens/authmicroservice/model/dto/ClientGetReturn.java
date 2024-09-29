package com.clubeevantagens.authmicroservice.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClientGetReturn {
    private String email;
    private String name;
    private String cpf;
    private String phoneNumber;
    private String socialName;
    private String preferences;
    private LocalDateTime nascimento;
    private String photo;
    private String cep;
    private String endereco;
    private String estado;
    private String cidade;

}
