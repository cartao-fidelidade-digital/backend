package com.clubeevantagens.authmicroservice.exception.general;

public class ClientUnavailableException extends RuntimeException {
    public ClientUnavailableException(String message) {
        super(message);
    }
}
