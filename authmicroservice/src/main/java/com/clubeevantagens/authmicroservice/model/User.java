package com.clubeevantagens.authmicroservice.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Table(name = "user", schema = "public")
@Getter
@Setter
@ToString
@EqualsAndHashCode
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private String dateTermsOfUse;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "cnpj")
    private String cnpj;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    table = "user"),

            inverseJoinColumns = @JoinColumn(
                    name="role_id",
                    referencedColumnName = "id",
                    unique = false,
                    table = "role"))
    private List<Role> roles;


}
