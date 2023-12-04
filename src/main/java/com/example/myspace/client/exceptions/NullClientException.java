package com.example.myspace.client.exceptions;

public class NullClientException extends ClientException {

    private static final String MESSAGE = "Null client provided";

    public NullClientException() {
        super(MESSAGE);
    }

}
