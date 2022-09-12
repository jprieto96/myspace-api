package com.example.myspace.note.exceptions;

public class NoteNotFoundException extends NoteException {

    private static final String MESSAGE = "Note not found";

    public NoteNotFoundException() {
        super(MESSAGE);
    }
}
