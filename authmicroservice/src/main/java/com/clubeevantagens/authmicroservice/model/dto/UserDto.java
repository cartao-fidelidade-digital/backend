package com.clubeevantagens.authmicroservice.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserDto {
    private String email;
    private String password;

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
