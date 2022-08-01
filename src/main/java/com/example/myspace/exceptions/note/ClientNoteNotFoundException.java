package com.example.myspace.exceptions.note;

import com.example.myspace.exceptions.client.ClientException;

public class EmptyNoteException extends ClientException {

    private static final String MESSAGE = "Empty note";

    public EmptyNoteException() {
        super(MESSAGE);
    }
}
