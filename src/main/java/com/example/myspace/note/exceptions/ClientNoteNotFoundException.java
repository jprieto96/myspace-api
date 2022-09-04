package com.example.myspace.note.exceptions;

public class ClientNoteNotFoundException extends NoteException {

    private static final String MESSAGE = "Note will not be created because the client introduced does not exist";

    public ClientNoteNotFoundException() {
        super(MESSAGE);
    }
}
