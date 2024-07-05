package com.clubeevantagens.authmicroservice.repository;
import com.clubeevantagens.authmicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    //@Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findUserByEmail(String email);

    Optional<User> findByResetPasswordToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.refreshToken = ?2 WHERE u.email = ?1")
    void updateRefreshTokenByEmail(String email, int refreshToken);

    @Query("SELECT u.refreshToken FROM User u WHERE u.id = ?1")
    Optional<Integer> findRefreshTokenById(Long userId);
}
