package com.example.myspace.exceptions.note;

public class NoteNotFoundException extends NoteException {

    private static final String MESSAGE = "Note not found";

    public NoteNotFoundException() {
        super(MESSAGE);
    }
}
