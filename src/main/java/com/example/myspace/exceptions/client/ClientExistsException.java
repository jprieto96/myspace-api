package com.example.myspace.exceptions.client;

public class ClientExistsException extends ValidationException {

    private static final String MESSAGE = "DNI already exists";

    public ClientExistsException() {
        super(MESSAGE);
    }
}
