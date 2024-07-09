package com.clubeevantagens.authmicroservice.controller;
import com.clubeevantagens.authmicroservice.model.Company;
import com.clubeevantagens.authmicroservice.model.CompanyDTO;
import com.clubeevantagens.authmicroservice.model.User;
import com.clubeevantagens.authmicroservice.security.JwtUtils;
import com.clubeevantagens.authmicroservice.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private JwtUtils jwtUtils;

    // CREATE
    @PostMapping("/register")
    public ResponseEntity<String> registerCompany(@RequestBody CompanyDTO companyDTO) {
        return companyService.registerCompany(companyDTO);
    }


    // READ
    @GetMapping("/all")
    public ResponseEntity<?> getAllCompanies() {
        return companyService.getAllCompanies();
    }


    // UPDATE
    @PutMapping
    public ResponseEntity<String> updateCompany(@RequestHeader Map<String,String> header, @RequestBody CompanyDTO newCompanyDTO) {
        var accessToken = header.get("authorization").substring(7);
        var accessTokenMap = jwtUtils.extractAccessToken(accessToken);
        var id = accessTokenMap.get("sub");
        return companyService.updateCompany(Long.valueOf(id),newCompanyDTO);
    }

    // DELETE
    @DeleteMapping
    public ResponseEntity<?> deleteCompany(@RequestHeader Map<String,String> header) {
        var accessToken = header.get("authorization").substring(7);
        var accessTokenMap = jwtUtils.extractAccessToken(accessToken);
        var id = accessTokenMap.get("sub");
        return companyService.deleteCompany(Long.valueOf(id));
    }

    // GET-COMPANY
    @GetMapping
    public ResponseEntity<?> getCompany(@RequestHeader Map<String,String> header) {
        var accessToken = header.get("authorization").substring(7);
        var accessTokenMap = jwtUtils.extractAccessToken(accessToken);
        var id = accessTokenMap.get("sub");
        return companyService.getCompany(Long.valueOf(id));
    }


}
