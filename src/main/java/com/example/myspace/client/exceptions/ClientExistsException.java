package com.example.myspace.client.exceptions;

public class ClientExistsException extends ClientException {

    private static final String MESSAGE = "Client already exists";

    public ClientExistsException() {
        super(MESSAGE);
    }
}
