package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.Company;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.repository.ClientRepository;
import com.clubeevantagens.authmicroservice.repository.UserRepository;
import com.clubeevantagens.authmicroservice.security.JwtUtils;
import com.clubeevantagens.authmicroservice.security.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${jwt.token.expiry}")
    private Long expiryToken;

    // DELETAR USER
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // LOGIN USER
    public ResponseEntity<?> loginUser(User user){

        try {
            UserDetails userDetails = userDetailService.loadUserByUsername(user.getEmail());// valida email

            if (passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {// valida password

                var tokenSession = jwtUtils.generateToken(userDetails);// gera "TokenSession"

                Map<String, String> payload = new HashMap<>();
                payload.put("tokenSession",tokenSession);
                payload.put("expiryTokenInSeconds",expiryToken.toString());

                return ResponseEntity.status(HttpStatus.ACCEPTED).body(payload);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email ou Senha incorreta");
            }

        } catch (UsernameNotFoundException e) {// email nao encontrado
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email ou Senha incorreta");
        } catch (Exception e1){ // outros erros
            e1.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ocorreu um erro inesperado");
        }
    }


}
