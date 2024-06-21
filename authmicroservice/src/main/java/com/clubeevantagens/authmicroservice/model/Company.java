package com.clubeevantagens.authmicroservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Table(name = "company", schema = "public")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Company {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false, length = 150)
    private String companyName;

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "type")
    private String type;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "terms_of_use", nullable = false)
    private boolean termsOfUse;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_terms_of_use")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Date dateTermsOfUse;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
