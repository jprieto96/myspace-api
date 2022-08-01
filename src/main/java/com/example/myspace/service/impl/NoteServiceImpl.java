package com.example.myspace.service.impl;

import com.example.myspace.dto.TopicDto;
import com.example.myspace.exceptions.note.ClientNoteNotFoundException;
import com.example.myspace.exceptions.note.EmptyNoteException;
import com.example.myspace.exceptions.note.EmptyNoteTopicException;
import com.example.myspace.exceptions.note.NoteException;
import com.example.myspace.model.ClientModel;
import com.example.myspace.model.NoteModel;
import com.example.myspace.model.NoteTopicModel;
import com.example.myspace.model.TopicModel;
import com.example.myspace.repository.ClientRepository;
import com.example.myspace.repository.NoteRepository;
import com.example.myspace.dto.NoteDto;
import com.example.myspace.repository.NoteTopicRepository;
import com.example.myspace.repository.TopicRepository;
import com.example.myspace.service.NoteService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private NoteTopicRepository noteTopicRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Optional<NoteDto> createNote(NoteDto noteDto) throws NoteException {
        Optional<ClientModel> optionalClientModel = clientRepository.findById(noteDto.getClientId());

        NoteException noteException = null;
        if(!optionalClientModel.isPresent()) {
            noteException = new ClientNoteNotFoundException();
        } else if (noteDto.getText() == null || noteDto.getText().isEmpty()) {
            noteException = new EmptyNoteException();
        } else if(noteDto.getNoteTopics() == null || noteDto.getNoteTopics().isEmpty() || isTopicNameEmpty(noteDto.getNoteTopics())) {
            noteException = new EmptyNoteTopicException();
        }

        if (Optional.ofNullable(noteException).isPresent()) {
            log.error("Note creation has not passed validation rules: {}",
                    noteException.getMessage());
            throw noteException;
        }

        log.debug("Note: {} has passed validation rules", noteDto.getId());

        NoteModel noteModel = new NoteModel();
        noteModel.setText(noteDto.getText());
        noteModel.setClientModel(optionalClientModel.get());
        NoteModel newNoteModel = noteRepository.save(noteModel);
        List<NoteTopicModel> noteTopicModelList = newNoteModel.getNoteTopicModels();
        for(TopicDto topicDto : noteDto.getNoteTopics()) {
            Optional<TopicModel> optionalTopicModel = topicRepository.findByName(topicDto.getName());
            TopicModel topicModel = null;
            if(!optionalTopicModel.isPresent()) {
                topicModel = topicRepository.save(topicDto.toEntity());
            }
            else {
                topicModel = optionalTopicModel.get();
            }
            NoteTopicModel noteTopicModel = new NoteTopicModel();
            noteTopicModel.setNoteModel(noteModel);
            noteTopicModel.setTopicModel(topicModel);
            noteTopicModelList.add(noteTopicModel);
            noteTopicRepository.save(noteTopicModel);
        }
        newNoteModel.setNoteTopicModels(noteTopicModelList);
        return Optional.ofNullable(newNoteModel.toDto());
    }

    private boolean isTopicNameEmpty(List<TopicDto> noteTopics) {
        for(TopicDto topicDto : noteTopics) {
            if(topicDto.getName().isEmpty()) return true;
        }
        return false;
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
