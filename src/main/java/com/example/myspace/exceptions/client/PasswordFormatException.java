package com.example.myspace.exceptions.client;

public class PasswordFormatException extends ValidationException {

    private static final String MESSAGE = "User password format is not valid";

    public PasswordFormatException() {
        super(MESSAGE);
    }
}
