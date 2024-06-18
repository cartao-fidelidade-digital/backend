package com.clubeevantagens.authmicroservice.repository;
import com.clubeevantagens.authmicroservice.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{

}
