package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.*;
import com.clubeevantagens.authmicroservice.repository.CompanyRepository;
import com.clubeevantagens.authmicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public ResponseEntity<String> registerCompany(CompanyDTO companyDTO){

        // Verifica se o email já está cadastrado
        if (userRepository.findUserByEmail(companyDTO.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email já cadastrado");
        }

        // Verifica se ambos CPF e CNPJ são nulos
        if (companyDTO.getCpf() == null && companyDTO.getCnpj() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF ou CNPJ deve ser fornecido");
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
        user.setEmail(companyDTO.getEmail());
        user.setPassword(companyDTO.getPassword());
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


        return ResponseEntity.status(HttpStatus.CREATED).body("Empresa salva com sucesso");
    }



    // BUSCAR TODOS AS EMPRESAS
    public ResponseEntity<?> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();

        List<CompanyDTO> retorno = new ArrayList<>();

        for(Company c : companies){
            CompanyDTO cAux = new CompanyDTO();
            cAux.setCompanyName(c.getCompanyName());
            cAux.setPassword(c.getUser().getPassword());
            cAux.setEmail(c.getUser().getEmail());
            cAux.setCpf(c.getCpf());
            cAux.setContactPhone(c.getContactPhone());
            cAux.setTermsOfUse(c.isTermsOfUse());
            cAux.setCnpj(c.getCnpj());
            cAux.setType(c.getType());
            cAux.setDateTermsOfUse(c.getDateTermsOfUse().toString());

            retorno.add(cAux);
        }
        return ResponseEntity.ok(retorno);
    }



    // EDITAR EMPRESA
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody CompanyDTO newCompanyDTO) {

        // Verifica se ambos CPF e CNPJ são nulos
        if (newCompanyDTO.getCpf() == null && newCompanyDTO.getCnpj() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF ou CNPJ deve ser fornecido");
        }

        // Valida CPF se não for nulo
        if (newCompanyDTO.getCpf() != null && !isValidCPF(newCompanyDTO.getCpf())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido");
        }

        // Valida CNPJ se não for nulo
        if (newCompanyDTO.getCnpj() != null && !isValidCNPJ(newCompanyDTO.getCnpj())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CNPJ inválido");
        }

        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            company.setCompanyName(newCompanyDTO.getCompanyName());
            company.setCnpj(newCompanyDTO.getCnpj());
            company.setCpf(newCompanyDTO.getCpf());
            company.setType(newCompanyDTO.getType());
            company.setContactPhone(newCompanyDTO.getContactPhone());

            companyRepository.save(company);

            return ResponseEntity.status(HttpStatus.CREATED).body("Empresa editada com sucesso");
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    // DELETAR EMPRESA
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if (companyOptional.isPresent()) {

            Company company = companyOptional.get();
            userService.deleteUser(company.getUser().getId());

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
}
