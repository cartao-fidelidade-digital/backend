package com.clubeevantagens.authmicroservice.repository;

import com.clubeevantagens.authmicroservice.model.Company;
import com.clubeevantagens.authmicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{
    Optional<Company> findCompanyByUser(User user);
    boolean existsByCpf(String cpf);
    boolean existsByCnpj(String cnpj);
}
