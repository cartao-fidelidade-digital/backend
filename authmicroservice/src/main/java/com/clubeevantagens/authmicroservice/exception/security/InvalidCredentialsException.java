package com.clubeevantagens.authmicroservice.exception.security;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
