package com.clubeevantagens.authmicroservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "client", schema = "public")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Client {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "cpf",unique = true)
    private String cpf;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "terms_of_use", nullable = false)
    private boolean termsOfUse;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_terms_of_use")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime dateTermsOfUse;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "social_name")
    private String socialName;

    @Column(name = "preferences")
    private String preferences;

    public LocalDateTime dateNow() {
        return LocalDateTime.now();
    }
}
