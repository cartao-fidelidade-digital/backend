package com.clubeevantagens.authmicroservice.exception;

public class UserDisabledException extends RuntimeException{

    public UserDisabledException(String message){
        super(message);
    }

}
