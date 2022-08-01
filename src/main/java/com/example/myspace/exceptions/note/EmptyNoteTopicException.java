package com.example.myspace.exceptions.note;

public class EmptyNoteTopicException extends NoteException {

    private static final String MESSAGE = "no topics selected for note";

    public EmptyNoteTopicException() {
        super(MESSAGE);
    }
}
