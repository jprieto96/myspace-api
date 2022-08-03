package com.example.myspace.controller;

import com.example.myspace.dto.ClientDto;
import com.example.myspace.dto.NoteDto;
import com.example.myspace.exceptions.client.ClientException;
import com.example.myspace.exceptions.note.NoteException;
import com.example.myspace.service.NoteService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
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

    @GetMapping(path = "/client/notes")
    public ResponseEntity<List<NoteDto>> listNotesByClient() throws NoteException {
        List<NoteDto> noteList = noteService.listNotesByClient();
        if(noteList == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(noteList);
    }

}
