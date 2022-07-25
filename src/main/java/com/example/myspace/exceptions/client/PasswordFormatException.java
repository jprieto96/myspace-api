package com.example.myspace.exceptions.client;

public class PasswordFormatException extends ClientException {

    private static final String MESSAGE = "User password format is not valid";

    public PasswordFormatException() {
        super(MESSAGE);
    }
}
