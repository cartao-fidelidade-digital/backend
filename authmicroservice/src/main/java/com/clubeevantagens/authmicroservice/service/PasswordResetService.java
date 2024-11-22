package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.messager.EmailProducer;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailProducer emailProducer;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ENVIAR "TOKEN" PARA EMAIL
    public ResponseEntity<?> sendPasswordResetTokenToEmail(String email)  {

        Optional<User> userOptional = userRepository.findUserByEmail(email);

        if (userOptional.isPresent()) {// email existe
            String token = UUID.randomUUID().toString();// gera token
            User u = userOptional.get();
            u.setResetPasswordToken(token);
            u.setResetPasswordExpiryDate(u.calculateExpiryDate(2880));

            userRepository.save(u);// salva "token" em "User"

            String emailBody = "Para resetar sua senha use o codigo abaixo:\n" + token + "\n\n\n" +
                    "Se você não fez uma solicitação, pode ignorar este e-mail.\n\n" +
                    "IMPORTANTE: Esse link possui validade de 48 horas, após esse prazo, solicite uma nova redefinição de senha repetindo o processo realizado.";

            emailProducer.sendEmailMessage(email, "Redefinição de Senha", emailBody);

            return ResponseEntity.ok().body("Solicitação de redefinição realizada. Voce recebera um email em alguns minutos");
        }else{// email não existe
            return ResponseEntity.notFound().build();
        }


    }


    // ENVIAR "TOKEN" E "PASSWORD" PARA VALIDAÇÂO
    public ResponseEntity<?> resetPassword(String token, String newPassword) {

        Optional<User> userOptional = userRepository.findByResetPasswordToken(token);


        if(userOptional.isPresent() ){// token existe

            User u = userOptional.get();

            // Valida Senha
            if(!u.isValidPassword(newPassword) ){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sua senha precisa conter 8 a 20 caracteres incluindo números, letras maiúsculas e minúsculas e caracteres especiais.");
            }

            if(!u.isExpired()){// token valido
                u.setPassword(newPassword);
                u.setResetPasswordExpiryDate(null);
                u.setResetPasswordToken(null);

                userRepository.save(u);

                return ResponseEntity.ok().body("Senha alterada com sucesso");
            }else{// token invalido
                u.setResetPasswordExpiryDate(null);
                u.setResetPasswordToken(null);

                userRepository.save(u);

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("token invalido");
            }


        }else{// token não existe
            return ResponseEntity.notFound().build();
        }

    }


    // RESETA COM SENHA ANTIGA
    public ResponseEntity<?> resetPasswordWithOldPassword(Long idUser, String oldPassword, String newPassword) {

        User user = new User();

        // Valide Senha nova
        if(!user.isValidPassword(newPassword) ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sua senha precisa conter 8 a 20 caracteres incluindo números, letras maiúsculas e minúsculas e caracteres especiais.");
        }

        // pegue senha do banco
        String atualPasswordEncrypt = userRepository.getPasswordUserById(idUser);

        if(passwordEncoder.matches(oldPassword,atualPasswordEncrypt)){// compare "senha do banco" com "senha antiga"
            userRepository.updatePasswordByIdUser(idUser,user.encodePassword(newPassword));

            return ResponseEntity.ok().body("Senha alterada com sucesso");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A senha antiga esta incorreta");
        }


    }
}