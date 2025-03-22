package com.clubeevantagens.authmicroservice.exception.general;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
