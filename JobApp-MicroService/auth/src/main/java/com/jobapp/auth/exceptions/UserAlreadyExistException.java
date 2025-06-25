package com.jobapp.auth.exceptions;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String email) {
        super("User with email " + email + " already exists. Try another one.");
    }
}
