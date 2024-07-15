package com.clubeevantagens.authmicroservice.controller;
import com.clubeevantagens.authmicroservice.document.CompanyDocs;
import com.clubeevantagens.authmicroservice.model.dto.CompanyDto;
import com.clubeevantagens.authmicroservice.model.dto.CompanyUpdateDto;
import com.clubeevantagens.authmicroservice.security.JwtUtils;
import com.clubeevantagens.authmicroservice.service.CompanyService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users/company")
public class CompanyController implements CompanyDocs {
    @Autowired
    private CompanyService companyService;

    @Autowired
    private JwtUtils jwtUtils;

    // CREATE
    @PostMapping("/register")
    @Override
    public ResponseEntity<?> registerCompany(@RequestBody CompanyDto companyDTO) {
        return companyService.registerCompany(companyDTO);
    }


    // READ
    @GetMapping("/all")
    public ResponseEntity<?> getAllCompanies() {
        return companyService.getAllCompanies();
    }


    // UPDATE
    @PutMapping
    @Override
    public ResponseEntity<String> updateCompany(@RequestHeader("Authorization") String authorization, @RequestBody CompanyUpdateDto newCompanyDto) {
        var accessToken = authorization.substring(7);
        var accessTokenMap = jwtUtils.extractAccessToken(accessToken);
        var id = accessTokenMap.get("sub");
        return companyService.updateCompany(Long.valueOf(id), newCompanyDto);
    }

    // DELETE
    @DeleteMapping
    @Override
    public ResponseEntity<?> deleteCompany(@RequestHeader("Authorization") String authorization) {
        var accessToken = authorization.substring(7);
        var accessTokenMap = jwtUtils.extractAccessToken(accessToken);
        var id = accessTokenMap.get("sub");
        return companyService.deleteCompany(Long.valueOf(id));
    }

    // GET-COMPANY
    @GetMapping
    @Override
    public ResponseEntity<?> getCompany(@RequestHeader("Authorization") String authorization) {
        var accessToken = authorization.substring(7);
        var accessTokenMap = jwtUtils.extractAccessToken(accessToken);
        var id = accessTokenMap.get("sub");
        return companyService.getCompany(Long.valueOf(id));
    }


}
