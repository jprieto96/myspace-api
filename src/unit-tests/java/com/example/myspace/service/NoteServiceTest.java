package com.example.myspace.service;

import com.example.myspace.dto.NoteDto;
import com.example.myspace.exceptions.note.ClientNoteNotFoundException;
import com.example.myspace.exceptions.note.NoteException;
import com.example.myspace.model.ClientModel;
import com.example.myspace.model.NoteModel;
import com.example.myspace.model.NoteTopicModel;
import com.example.myspace.model.TopicModel;
import com.example.myspace.repository.ClientRepository;
import com.example.myspace.repository.NoteRepository;
import com.example.myspace.repository.NoteTopicRepository;
import com.example.myspace.repository.TopicRepository;
import com.example.myspace.service.impl.NoteServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import static org.mockito.Mockito.when;

class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private NoteTopicRepository noteTopicRepository;

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private NoteService noteService = new NoteServiceImpl();

    private AutoCloseable autoCloseable;
    private NoteModel noteIn;
    private NoteModel noteOut;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        String salt = "s@cr@t";
        String password = "Test1234";
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        ClientModel clientModel = new ClientModel();
        clientModel.setEmail("test@mail.com");
        clientModel.setUsername("test");
        clientModel.setPassword(passwordEncoder.encode(new String(Base64.getEncoder().encode((password + salt).getBytes()))));
        clientModel.setName("Test");
        clientModel.setActive(true);
        clientModel.setPasswordSalt(new String(Base64.getEncoder().encode(salt.getBytes())));

        TopicModel noteTopicModelFitness = new TopicModel();
        noteTopicModelFitness.setName("Fitness");

        NoteTopicModel noteTopicModel = new NoteTopicModel();
        noteTopicModel.setNoteModel(noteIn);
        noteTopicModel.setTopicModel(noteTopicModelFitness);

        noteIn = new NoteModel();
        noteIn.setActive(true);
        noteIn.setText("Test note...");
        noteIn.setClientModel(clientModel);
        noteIn.setNoteTopicModels(Arrays.asList(noteTopicModel));

        noteOut = new NoteModel();
        noteOut.setId(new Long(1));
        noteOut.setActive(true);
        noteOut.setText("Test note...");
        noteOut.setClientModel(clientModel);
        noteOut.setNoteTopicModels(Arrays.asList(noteTopicModel));
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void createNoteOK() {
        when(clientRepository.findByUsername(noteIn.getClientModel().getUsername())).thenReturn(Optional.ofNullable(noteOut.getClientModel()));
        when(noteRepository.save(noteIn)).thenReturn(noteOut);
        when(topicRepository.findByName(noteIn.getNoteTopicModels().get(0).getTopicModel().getName())).thenReturn(Optional.ofNullable(noteOut.getNoteTopicModels().get(0).getTopicModel()));
        when(topicRepository.save(noteIn.getNoteTopicModels().get(0).getTopicModel())).thenReturn(noteOut.getNoteTopicModels().get(0).getTopicModel());
        when(noteTopicRepository.save(noteIn.getNoteTopicModels().get(0))).thenReturn(noteOut.getNoteTopicModels().get(0));

        try {
            Optional<NoteDto> optionalNoteDto = noteService.createNote(noteIn.toDto());
            Assertions.assertTrue(optionalNoteDto.isPresent());
            NoteDto noteDto = optionalNoteDto.get();
            Assertions.assertEquals(noteDto.getId(), 1);
            Assertions.assertEquals(noteDto.getText(), noteOut.getText());
            Assertions.assertEquals(noteDto.getClientId(), noteOut.getClientModel().getId());
            Assertions.assertTrue(noteDto.isActive());
        } catch (NoteException e) {
            e.printStackTrace();
        }
    }

    @Test
    void createNoteClientConflict() {
        when(clientRepository.findByUsername(noteIn.getClientModel().getUsername())).thenReturn(Optional.ofNullable(null));

        try {
            noteService.createNote(noteIn.toDto());
        } catch (NoteException e) {
            Assertions.assertEquals(e.getClass(), ClientNoteNotFoundException.class);
            Assertions.assertEquals(e.getMessage(), "Note will not be created because the client introduced does not exist");
        }
    }

    @Test
    void deleteNote() {
    }

    @Test
    void showDetails() {
    }

    @Test
    void listNotesByClient() {
    }

    @Test
    void updateNote() {
    }

}