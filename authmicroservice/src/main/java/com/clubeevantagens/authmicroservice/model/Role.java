package com.clubeevantagens.authmicroservice.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.List;

@Table(name = "role", schema = "public")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

}
