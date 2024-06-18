package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.Company;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.repository.ClientRepository;
import com.clubeevantagens.authmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

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
    public ResponseEntity<String> loginUser(User user){

        Optional<User> userOptional = userRepository.findUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {// email existe
            User u = userOptional.get();

            if(!u.getPassword().equals(user.getPassword()) ){// senha incorreta
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email ou Senha incorreta");
            }

            return ResponseEntity.ok().build();
        } else {// email nao existe
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email ou Senha incorreta");
        }
    }


}
