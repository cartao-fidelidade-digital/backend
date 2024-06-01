package com.clubeevantagens.authmicroservice.controller;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.model.UserDTO;
import com.clubeevantagens.authmicroservice.repository.UserRepository;
import com.clubeevantagens.authmicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // CREATE
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }


    // READ
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }


    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO newUserDTO) {
       return userService.updateUser(id,newUserDTO);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }




}
