package com.clubeevantagens.authmicroservice.repository;

import com.clubeevantagens.authmicroservice.model.data.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}