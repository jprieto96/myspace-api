package com.example.myspace.note;

import com.example.myspace.topic.TopicDto;
import com.example.myspace.client.ClientModel;
import com.example.myspace.notetopic.NoteTopicModel;
import com.example.myspace.topic.TopicModel;
import com.example.myspace.client.ClientRepository;
import com.example.myspace.note.exceptions.*;
import com.example.myspace.notetopic.NoteTopicRepository;
import com.example.myspace.topic.TopicRepository;
import com.example.myspace.security.UserPrinciple;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
        // Get the username of user logged in
        String username = getLoggedUser();

        Optional<ClientModel> optionalClientModel = clientRepository.findByUsername(username);

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
        noteModel.setActive(true);
        noteModel.setClientModel(optionalClientModel.get());
        NoteModel newNoteModel = noteRepository.save(noteModel);
        List<NoteTopicModel> noteTopicModelList = newNoteModel.getNoteTopicModels() != null ? newNoteModel.getNoteTopicModels() : new ArrayList<>();
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
            NoteTopicModel newNoteTopicModel = noteTopicRepository.save(noteTopicModel);
            noteTopicModelList.add(newNoteTopicModel);
        }
        newNoteModel.setNoteTopicModels(noteTopicModelList);
        return Optional.ofNullable(newNoteModel.toDto());
    }

    @Override
    public void deleteNote(Long noteId) throws NoteException {
        Optional<NoteModel> optionalNoteModel = noteRepository.findById(noteId);
        if(!optionalNoteModel.isPresent()) {
            log.error("Note does not exist");
            throw new NoteNotFoundException();
        }

        noteTopicRepository.deleteNoteTopicModelByNoteModelId(noteId);
        // Get topics that are going to be without an associated topic
        List<TopicModel> topicsToDelete = topicRepository.getTopicsToDelete(noteId);
        topicRepository.deleteAllInBatch(topicsToDelete);

        // Delete the note
        noteRepository.delete(optionalNoteModel.get());
    }

    @Override
    public List<NoteDto> listNotesByClient() throws NoteException {
        // Get the username of user logged in
        String username = getLoggedUser();

        Optional<ClientModel> optionalClientModel = clientRepository.findByUsername(username);

        NoteException noteException = null;
        if(!optionalClientModel.isPresent()) {
            noteException = new ClientNoteNotFoundException();
        }

        if (Optional.ofNullable(noteException).isPresent()) {
            log.error("List notes by client has not passed validation rules: {}",
                    noteException.getMessage());
            throw noteException;
        }

        Long clientId = optionalClientModel.get().getId();
        log.debug("Notes for client {} has passed validation rules", clientId);
        List<NoteModel> noteModelList = noteRepository.findAllByClientId(clientId);

        return noteModelList.stream().map(NoteModel::toDto).toList();
    }

    private String getLoggedUser() {
        if(SecurityContextHolder.getContext() == null ||
                SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }

        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrinciple.getUsername();
    }

    private boolean isTopicNameEmpty(List<TopicDto> noteTopics) {
        for(TopicDto topicDto : noteTopics) {
            if(topicDto.getName().isEmpty()) return true;
        }
        return false;
    }

}
