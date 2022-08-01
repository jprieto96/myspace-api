package com.example.myspace.service;

import com.example.myspace.dto.NoteDto;
import com.example.myspace.exceptions.note.NoteException;

import java.util.List;
import java.util.Optional;

public interface NoteService {

    Optional<NoteDto> createNote(NoteDto noteDto) throws NoteException;
    NoteDto deleteNote(Long id) throws Exception;
    NoteDto showDetails(Long id) throws Exception;
    List<NoteDto> listNotes();
    NoteDto updateNote(NoteDto noteDto) throws Exception;

}
