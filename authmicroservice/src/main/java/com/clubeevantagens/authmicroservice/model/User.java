package com.clubeevantagens.authmicroservice.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Table(name = "users", schema = "public")
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

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @JsonIgnore
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

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reset_password_expiry_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime resetPasswordExpiryDate;

    public LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {
        LocalDateTime now = LocalDateTime.now();
        return now.plusMinutes(expiryTimeInMinutes);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.resetPasswordExpiryDate);
    }
}
