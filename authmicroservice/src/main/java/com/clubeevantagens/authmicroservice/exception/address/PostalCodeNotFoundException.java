package com.clubeevantagens.authmicroservice.exception.address;

public class PostalCodeNotFoundException extends RuntimeException {
    public PostalCodeNotFoundException(String message) {
        super(message);
    }
}
