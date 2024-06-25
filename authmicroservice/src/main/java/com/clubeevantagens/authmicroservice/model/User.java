package com.clubeevantagens.authmicroservice.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


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

    // CRIA DATA DE "resetPasswordExpiryDate" PARA 48h SEGUINTES
    public LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {
        LocalDateTime now = LocalDateTime.now();
        return now.plusMinutes(expiryTimeInMinutes);
    }

    // VERIFICA DATA DE "resetPasswordExpiryDate"
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.resetPasswordExpiryDate);
    }

    // VALIDA SENHA
    public boolean isValidPassword(String password) {
        // Verifica se a senha tem entre 8 e 20 caracteres
        if (password.length() < 8 || password.length() > 20) {
            return false;
        }

        // Verifica se a senha contém pelo menos uma letra maiúscula
        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        // Verifica se a senha contém pelo menos uma letra minúscula
        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        // Verifica se a senha contém pelo menos um número
        if (!password.matches(".*\\d.*")) {
            return false;
        }

        // Verifica se a senha contém pelo menos um caractere especial
        if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            return false;
        }

        // Se passar todas as verificações, a senha é válida
        return true;
    }

    // VALIDA EMAIL
    public boolean isValidEmail(String email) {
        // Regex para verificar o formato do email
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compilar a regex em um padrão
        Pattern pattern = Pattern.compile(emailRegex);

        // Se o email for nulo, retorna falso
        if (email == null) {
            return false;
        }

        // Retorna se o email corresponde ao padrão
        return pattern.matcher(email).matches();
    }
}
