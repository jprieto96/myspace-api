package com.example.myspace.service.impl;

import com.example.myspace.repository.NoteRepository;
import com.example.myspace.dto.NoteDto;
import com.example.myspace.service.NoteService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Log4j2
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Override
    public NoteDto createNote(NoteDto noteDto) throws Exception {
        return null;
    }

    @Override
    public NoteDto deleteNote(Long id) throws Exception {
        return null;
    }

    @Override
    public NoteDto showDetails(Long id) throws Exception {
        return null;
    }

    @Override
    public List<NoteDto> listNotes() {
        return null;
    }

    @Override
    public NoteDto updateNote(NoteDto noteDto) throws Exception {
        return null;
    }

}
