package com.clubeevantagens.authmicroservice.exception.general;

public class UniqueValueInUse extends RuntimeException {
    public UniqueValueInUse(String message) {
        super(message);
    }
}
