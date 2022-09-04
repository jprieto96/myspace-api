package com.example.myspace.note;

import com.example.myspace.note.NoteDto;
import com.example.myspace.note.exceptions.NoteException;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    Optional<NoteDto> createNote(NoteDto noteDto) throws NoteException;
    NoteDto deleteNote(Long id) throws NoteException;
    NoteDto showDetails(Long id) throws NoteException;
    List<NoteDto> listNotesByClient() throws NoteException;
    NoteDto updateNote(NoteDto noteDto) throws NoteException;

}
