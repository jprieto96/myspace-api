package com.example.myspace.client.exceptions;

public class EmptyUserNameException extends ClientException {

    private static final String MESSAGE = "Empty username";

    public EmptyUserNameException() {
        super(MESSAGE);
    }
}
