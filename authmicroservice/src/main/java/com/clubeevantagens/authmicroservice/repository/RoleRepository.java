package com.clubeevantagens.authmicroservice.repository;
import com.clubeevantagens.authmicroservice.model.Role;
import com.clubeevantagens.authmicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
}
