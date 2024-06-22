package com.clubeevantagens.authmicroservice.controller;
import com.clubeevantagens.authmicroservice.model.Company;
import com.clubeevantagens.authmicroservice.model.CompanyDTO;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    // CREATE
    @PostMapping("/register")
    public ResponseEntity<String> registerCompany(@RequestBody CompanyDTO companyDTO) {
        return companyService.registerCompany(companyDTO);
    }


    // READ
    @GetMapping
    public ResponseEntity<?> getAllCompanies() {
        return companyService.getAllCompanies();
    }


    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@PathVariable Long id, @RequestBody CompanyDTO newCompanyDTO) {
       return companyService.updateCompany(id,newCompanyDTO);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable Long id) {
        return companyService.deleteCompany(id);
    }




}
