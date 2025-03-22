package com.clubeevantagens.authmicroservice.repository;

import com.clubeevantagens.authmicroservice.model.data.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    void deleteByUserId(Long userId);
}