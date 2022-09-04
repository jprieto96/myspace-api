package com.example.myspace.note.exceptions;

public class EmptyNoteTopicException extends NoteException {

    private static final String MESSAGE = "no topics selected for note";

    public EmptyNoteTopicException() {
        super(MESSAGE);
    }
}
