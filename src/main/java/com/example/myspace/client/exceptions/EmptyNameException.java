package com.example.myspace.client.exceptions;

public class EmptyNameException extends ClientException {

    private static final String MESSAGE = "Empty name";

    public EmptyNameException() {
        super(MESSAGE);
    }
}
