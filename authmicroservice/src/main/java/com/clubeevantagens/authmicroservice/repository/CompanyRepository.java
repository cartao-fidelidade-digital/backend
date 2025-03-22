package com.clubeevantagens.authmicroservice.repository;

import com.clubeevantagens.authmicroservice.model.data.Company;
import com.clubeevantagens.authmicroservice.model.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long>{
    Optional<Company> findCompanyByUser(User user);
    boolean existsByCnpj(String cnpj);
    boolean existsByUserEmail(String email);

    Optional<Company> findByCnpj(String cnpj);
}
