package com.example.myspace.exceptions.note;

public class EmptyNoteException extends NoteException {

    private static final String MESSAGE = "Empty note";

    public EmptyNoteException() {
        super(MESSAGE);
    }
}
