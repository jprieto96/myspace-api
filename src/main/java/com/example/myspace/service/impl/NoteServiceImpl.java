package com.example.myspace.service.impl;

import com.example.myspace.dao.NoteDao;
import com.example.myspace.dto.NoteDto;
import com.example.myspace.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteDao noteDao;

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
