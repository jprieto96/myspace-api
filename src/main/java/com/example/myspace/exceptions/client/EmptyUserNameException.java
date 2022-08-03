package com.example.myspace.exceptions.client;

public class EmptyUserNameException extends ClientException {

    private static final String MESSAGE = "Empty username";

    public EmptyUserNameException() {
        super(MESSAGE);
    }
}
