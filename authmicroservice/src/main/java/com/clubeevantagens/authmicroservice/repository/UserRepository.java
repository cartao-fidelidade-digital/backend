package com.clubeevantagens.authmicroservice.repository;
import com.clubeevantagens.authmicroservice.model.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findUserByEmail(String email);

    Optional<User> findByResetPasswordTokenToken(String token);
}