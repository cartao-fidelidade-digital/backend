package com.clubeevantagens.authmicroservice.model.data;

import com.clubeevantagens.authmicroservice.model.enums.CategoryType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private CategoryType name;

    @OneToMany
    private List<Company> companies;

    @ManyToMany
    private List<Client> clients;
}