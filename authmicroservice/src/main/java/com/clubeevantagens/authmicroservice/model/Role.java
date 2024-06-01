package com.clubeevantagens.authmicroservice.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "role", schema = "public")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
public class Role {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;
}
