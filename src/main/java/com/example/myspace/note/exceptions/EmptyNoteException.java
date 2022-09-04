package com.example.myspace.note.exceptions;

public class EmptyNoteException extends NoteException {

    private static final String MESSAGE = "Empty note";

    public EmptyNoteException() {
        super(MESSAGE);
    }
}
