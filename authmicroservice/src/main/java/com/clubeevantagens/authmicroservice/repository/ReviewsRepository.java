package com.clubeevantagens.authmicroservice.repository;

import com.clubeevantagens.authmicroservice.model.Reviews;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewsRepository extends MongoRepository<Reviews, Long> {
}
