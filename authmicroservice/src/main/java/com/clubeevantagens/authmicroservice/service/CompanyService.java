package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.*;
import com.clubeevantagens.authmicroservice.model.dto.CompanyDto;
import com.clubeevantagens.authmicroservice.model.dto.CompanyUpdateDto;
import com.clubeevantagens.authmicroservice.model.dto.UserDto;
import com.clubeevantagens.authmicroservice.repository.CompanyRepository;
import com.clubeevantagens.authmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserService userService;

    // CRIAR EMPRESA
    public ResponseEntity<?> registerCompany(CompanyDto companyDTO){

        // Verifica se o email já está cadastrado
        if (userRepository.findUserByEmail(companyDTO.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email já cadastrado");
        }

        // Valida CPF se não for nulo
        if (companyDTO.getCpf() != null && !isValidCPF(companyDTO.getCpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido");
        }

        // Valida CNPJ se não for nulo
        if (companyDTO.getCnpj() != null && !isValidCNPJ(companyDTO.getCnpj())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CNPJ inválido");
        }


        User user = new User();

        // Valida Email
        if(!user.isValidEmail(companyDTO.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email inválido");
        }
        // Valida Senha
        if(!user.isValidPassword(companyDTO.getPassword()) ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sua senha precisa conter 8 a 20 caracteres incluindo números, letras maiúsculas e minúsculas e caracteres especiais");
        }

        user.setEmail(companyDTO.getEmail());
        user.setPassword(user.encodePassword(companyDTO.getPassword()));// criptografa password
        user.setActive(true);

        List<Role> roles = new ArrayList<>();
        roles.add(new Role(2L,"COMPANY",null));

        // add roles
        user.setRoles(roles);

        // Salva "User" no banco
        userRepository.save(user);

        Company company = new Company();
        company.setUser(user);
        company.setCompanyName(companyDTO.getCompanyName());
        company.setCpf(companyDTO.getCpf());
        company.setCnpj(companyDTO.getCnpj());
        company.setType(companyDTO.getType());
        company.setContactPhone(companyDTO.getContactPhone());
        company.setTermsOfUse(companyDTO.isTermsOfUse());
        company.setDateTermsOfUse(company.dateNow());
        // Salva "Company" no banco
        companyRepository.save(company);

        // Realizar Login
        var retorno = userService.loginUser(new UserDto(companyDTO.getEmail(),companyDTO.getPassword()));

        return retorno;
    }



    // BUSCAR TODOS AS EMPRESAS
    public ResponseEntity<?> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();

        List<CompanyDto> retorno = new ArrayList<>();

        for(Company c : companies){
            CompanyDto cAux = new CompanyDto();
            cAux.setCompanyName(c.getCompanyName());
            cAux.setPassword(c.getUser().getPassword());
            cAux.setEmail(c.getUser().getEmail());
            cAux.setCpf(c.getCpf());
            cAux.setContactPhone(c.getContactPhone());
            cAux.setTermsOfUse(c.isTermsOfUse());
            cAux.setCnpj(c.getCnpj());
            cAux.setType(c.getType());

            retorno.add(cAux);
        }
        return ResponseEntity.ok(retorno);
    }



    // EDITAR EMPRESA
    public ResponseEntity<String> updateCompany(Long idUser, CompanyUpdateDto newCompanyDto) {

        // Valida CPF se não for nulo
        if (newCompanyDto.getCpf() != null && !isValidCPF(newCompanyDto.getCpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido");
        }

        // Valida CNPJ se não for nulo
        if (newCompanyDto.getCnpj() != null && !isValidCNPJ(newCompanyDto.getCnpj())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CNPJ inválido");
        }

        // Verifica se "user" existe
        var userOptional = userRepository.findById(idUser);
        if(userOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        // Verifica se "company" exite
        Optional<Company> companyOptional = companyRepository.findCompanyByUser(userOptional.get());
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            Optional.ofNullable(newCompanyDto.getCompanyName()).ifPresent(company::setCompanyName);
            Optional.ofNullable(newCompanyDto.getCnpj()).ifPresent(company::setCnpj);
            Optional.ofNullable(newCompanyDto.getCpf()).ifPresent(company::setCpf);
            Optional.ofNullable(newCompanyDto.getType()).ifPresent(company::setType);
            Optional.ofNullable(newCompanyDto.getContactPhone()).ifPresent(company::setContactPhone);

            companyRepository.save(company);// salva "company"

            return ResponseEntity.status(HttpStatus.CREATED).body("Empresa editada com sucesso");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    // DELETAR EMPRESA
    public ResponseEntity<?> deleteCompany(Long idUser) {
        var userOptional = userRepository.findById(idUser);
        if (userOptional.isPresent()) {
            userService.deleteUser(idUser);// deleta "user"
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    // GET-COMPANY
    public ResponseEntity<?> getCompany(Long idUser){
        var userOptional = userRepository.findById(idUser);
        if (userOptional.isPresent()) {
            var company = companyRepository.findCompanyByUser(userOptional.get()).get();
            var payload = new HashMap<>();
            payload.put("email",company.getUser().getEmail());
            payload.put("companyName",company.getCompanyName());
            payload.put("cpf",company.getCpf());
            payload.put("cnpj",company.getCnpj());
            payload.put("type",company.getType());
            payload.put("contactPhone",company.getContactPhone());
            return ResponseEntity.ok(payload);
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
}
