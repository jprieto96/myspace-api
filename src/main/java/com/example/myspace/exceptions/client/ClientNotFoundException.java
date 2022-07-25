package com.example.myspace.exceptions.client;

public class ClientNotFoundException extends ClientException {

    private static final String MESSAGE = "Client not found";

    public ClientNotFoundException() {
        super(MESSAGE);
    }
}
