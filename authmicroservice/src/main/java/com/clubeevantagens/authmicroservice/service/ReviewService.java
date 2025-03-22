package com.clubeevantagens.authmicroservice.service;

import com.clubeevantagens.authmicroservice.exception.general.EntityNotFoundException;
import com.clubeevantagens.authmicroservice.model.data.Client;
import com.clubeevantagens.authmicroservice.model.data.Company;
import com.clubeevantagens.authmicroservice.model.data.Review;
import com.clubeevantagens.authmicroservice.model.dto.request.AdminReviewRequestDto;
import com.clubeevantagens.authmicroservice.model.dto.request.ReviewRequestDto;
import com.clubeevantagens.authmicroservice.repository.ClientRepository;
import com.clubeevantagens.authmicroservice.repository.CompanyRepository;
import com.clubeevantagens.authmicroservice.repository.ReviewRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ClientRepository clientRepository;
    private final CompanyRepository companyRepository;

    public ReviewService(ReviewRepository reviewRepository, ClientRepository clientRepository, CompanyRepository companyRepository) {
        this.reviewRepository = reviewRepository;
        this.clientRepository = clientRepository;
        this.companyRepository = companyRepository;
    }

    public String registerReview(ReviewRequestDto dto, Authentication authentication){

        Client client = (Client) authentication.getPrincipal();

        Company company = companyRepository.findById(dto.companyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found!"));

        Review review = new Review();
        review.setClient(client);
        review.setCompany(company);
        review.setStars(dto.stars());
        review.setComment(dto.comment());
        review.setDate(LocalDateTime.now());
        review.setFavorite(false);

        reviewRepository.save(review);

        return "Review saved";
    }

    public String registerReview(Long idClient, Long idCompany, AdminReviewRequestDto dto){
        Client client = clientRepository.findById(idClient)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        Company company = companyRepository.findById(idCompany)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada"));

        Review review = new Review();
        review.setClient(client);
        review.setCompany(company);
        review.setStars(dto.stars());
        review.setComment(dto.comment());
        review.setDate(LocalDateTime.now());

        reviewRepository.save(review);

        return "Review saved";
    }
}
