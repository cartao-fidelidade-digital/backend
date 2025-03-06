package com.clubeevantagens.authmicroservice.repository;
import com.clubeevantagens.authmicroservice.model.Client;
import com.clubeevantagens.authmicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
    Optional<Client> findClientByUser(User user);
    boolean existsByCpf(String cpf);
}
