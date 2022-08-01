package com.example.myspace.controller;

import com.example.myspace.dto.ClientDto;
import com.example.myspace.dto.NoteDto;
import com.example.myspace.exceptions.client.ClientException;
import com.example.myspace.exceptions.note.NoteException;
import com.example.myspace.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class NoteController {

    @Autowired
    private NoteService noteService;

    @PostMapping("/note")
    public ResponseEntity<?> register(@RequestBody NoteDto noteDto) {
        try {
            Optional<NoteDto> newNoteDtoOptional = noteService.createNote(noteDto);
            if (!newNoteDtoOptional.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Note not created");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(newNoteDtoOptional.get());
        } catch (NoteException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

}
