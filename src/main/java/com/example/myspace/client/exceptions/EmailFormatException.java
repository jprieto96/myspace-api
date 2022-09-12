package com.example.myspace.client.exceptions;

public class EmailFormatException extends ClientException {

    private static final String MESSAGE = "User email format is not valid";

    public EmailFormatException() {
        super(MESSAGE);
    }
}
