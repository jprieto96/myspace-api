package com.example.myspace.exceptions.client;

public class EmptyNameException extends ClientException {

    private static final String MESSAGE = "Empty name";

    public EmptyNameException() {
        super(MESSAGE);
    }
}
