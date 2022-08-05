package com.example.myspace.dto;

import com.example.myspace.model.NoteModel;
import com.example.myspace.model.NoteTopicModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NoteDto {

    private Long id;
    private String text;

    private boolean active;
    private List<TopicDto> noteTopics;
    private Long clientId;

    public NoteDto() {
    }

    public NoteDto(NoteModel noteModel) {
        this.id = noteModel.getId();
        this.text = noteModel.getText();
        this.active = noteModel.isActive();
        this.noteTopics = getNoteTopics(noteModel);
        this.clientId = noteModel.getClientModel() != null ? noteModel.getClientModel().getId() : null;
    }

    private List<TopicDto> getNoteTopics(NoteModel noteModel) {
        List<TopicDto> topicDtoList = new ArrayList<>();
        for(NoteTopicModel noteTopicModel : noteModel.getNoteTopicModels()) {
            topicDtoList.add(noteTopicModel.getTopicModel().toDto());
        }
        return topicDtoList;
    }
}
