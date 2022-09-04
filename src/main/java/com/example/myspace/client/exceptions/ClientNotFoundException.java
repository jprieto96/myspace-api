package com.example.myspace.client.exceptions;

public class ClientNotFoundException extends ClientException {

    private static final String MESSAGE = "Client not found";

    public ClientNotFoundException() {
        super(MESSAGE);
    }
}
