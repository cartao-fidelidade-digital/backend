package com.clubeevantagens.authmicroservice.exception.user;

public class UserDisabledException extends RuntimeException{

    public UserDisabledException(String message){
        super(message);
    }

}
