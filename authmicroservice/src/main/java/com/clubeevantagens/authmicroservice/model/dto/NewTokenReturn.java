package com.clubeevantagens.authmicroservice.model.dto;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class NewTokenReturn {
    private String newAccessToken;
    private String newRefreshToken;
}
