package com.clubeevantagens.authmicroservice.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Table(name = "user", schema = "public")
@Data
@Entity
public class User {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "terms_of_use", nullable = false)
    private boolean termsOfUse;

    @Column(name = "date_terms_of_use")
    private Date dateTermsOfUse;
}
