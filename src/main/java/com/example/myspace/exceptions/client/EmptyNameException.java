package com.example.myspace.exceptions.client;

public class EmptyNameException extends ValidationException {

    private static final String MESSAGE = "Empty username";

    public EmptyNameException() {
        super(MESSAGE);
    }
}
