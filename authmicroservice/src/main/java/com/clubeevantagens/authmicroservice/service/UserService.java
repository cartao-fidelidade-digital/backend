package com.clubeevantagens.authmicroservice.service;
import com.clubeevantagens.authmicroservice.model.Role;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.model.UserDTO;
import com.clubeevantagens.authmicroservice.repository.RoleRepository;
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
    private RoleRepository roleRepository;

    // CRIAR USUARIO
    public ResponseEntity<String> registerUser(UserDTO userDTO){

        // Verifica se o email já está cadastrado
        if (userRepository.findUserByEmail(userDTO.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email já cadastrado");
        }

        // Valida CPF se não for nulo
        if (userDTO.getCpf() != null && !isValidCPF(userDTO.getCpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido");
        }

        // Valida CNPJ se não for nulo
        if (userDTO.getCnpj() != null && !isValidCNPJ(userDTO.getCnpj())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CNPJ inválido");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setTermsOfUse(userDTO.isTermsOfUse());
        user.setDateTermsOfUse(userDTO.getDateTermsOfUse());
        user.setCpf(userDTO.getCpf());
        user.setCnpj(userDTO.getCnpj());
        user.setRoles(changeListLongForRole(userDTO.getRoles()));

        // Salva o usuário no banco de dados
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario salvo com sucesso");
    }



    // BUSCAR TODOS OS USUARIOS
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }



    // EDITAR USUARIO
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO newUserDTO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setName(newUserDTO.getName());
            user.setEmail(newUserDTO.getEmail());
            user.setPassword(newUserDTO.getPassword());
            user.setTermsOfUse(newUserDTO.isTermsOfUse());
            user.setDateTermsOfUse(newUserDTO.getDateTermsOfUse());
            user.setCpf(newUserDTO.getCpf());
            user.setCnpj(newUserDTO.getCnpj());
            user.setRoles(changeListLongForRole(newUserDTO.getRoles()));

            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    // DELETAR USUARIO
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // Remove Formatação de CPF ou CNPJ
    public String removeFormatting(String value) {
        return value.replaceAll("[^\\d]", ""); // Remove todos os caracteres que não são dígitos
    }


    // Validador de CPF
    public boolean isValidCPF(String cpf) {

        cpf = removeFormatting(cpf); // Remove formatação

        if (cpf == null || cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) return false;

        try {
            int sum = 0;
            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int firstCheckDigit = 11 - (sum % 11);
            if (firstCheckDigit > 9) firstCheckDigit = 0;
            if (firstCheckDigit != Character.getNumericValue(cpf.charAt(9))) return false;

            sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            int secondCheckDigit = 11 - (sum % 11);
            if (secondCheckDigit > 9) secondCheckDigit = 0;
            return secondCheckDigit == Character.getNumericValue(cpf.charAt(10));
        } catch (Exception e) {
            return false;
        }
    }


    // Validador de CNPJ
    public boolean isValidCNPJ(String cnpj) {

        cnpj = removeFormatting(cnpj); // Remove formatação

        if (cnpj == null || cnpj.length() != 14 || cnpj.matches("(\\d)\\1{13}")) return false;

        try {
            int[] weights = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int sum = 0;
            for (int i = 0; i < 12; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weights[i + 1];
            }
            int firstCheckDigit = 11 - (sum % 11);
            if (firstCheckDigit > 9) firstCheckDigit = 0;
            if (firstCheckDigit != Character.getNumericValue(cnpj.charAt(12))) return false;

            sum = 0;
            for (int i = 0; i < 13; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weights[i];
            }
            int secondCheckDigit = 11 - (sum % 11);
            if (secondCheckDigit > 9) secondCheckDigit = 0;
            return secondCheckDigit == Character.getNumericValue(cnpj.charAt(13));
        } catch (Exception e) {
            return false;
        }
    }

    // Converte uma lista do tipo Long para Role
    public List<Role> changeListLongForRole(List<Long> listLong){
        List<Role> roles = new ArrayList<>();

        if(listLong != null || !listLong.isEmpty()){
            roles = listLong
                    .stream()
                    .map(roleId -> roleRepository.findById(roleId).orElse(null))
                    .collect(Collectors.toList());
        }

        return roles;
    }


}
