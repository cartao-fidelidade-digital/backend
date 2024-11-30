package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.model.Client;
import com.clubeevantagens.authmicroservice.model.Company;
import com.clubeevantagens.authmicroservice.model.Reviews;
import com.clubeevantagens.authmicroservice.model.dto.ReviewDto;
import com.clubeevantagens.authmicroservice.repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewsService {

    @Autowired
    ReviewsRepository reviewsRepository;

    // CRIE AVALIAÇÃO
    public ResponseEntity<?> registerReview(ReviewDto reviewDto){


        Reviews reviews = new Reviews();
        reviews.setClient(reviewDto.getClient());
        reviews.setCompany(reviewDto.getCompany());
        reviews.setStars(reviewDto.getStars());
        reviews.setComment(reviewDto.getComment());
        reviews.setDate(LocalDateTime.now());
        reviews.setFavorite(false);

        reviewsRepository.save(reviews);

        return ResponseEntity.status(HttpStatus.CREATED).body("Avaliação realizada com sucesso");
    }


}
