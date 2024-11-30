package com.clubeevantagens.authmicroservice.model.dto;

import com.clubeevantagens.authmicroservice.model.Client;
import com.clubeevantagens.authmicroservice.model.Company;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ReviewDto {
    private Long client;
    private Long company;
    private int stars;
    private String comment;

}
