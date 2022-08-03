package com.example.myspace.dto;

import com.example.myspace.model.TopicModel;
import lombok.Data;

@Data
public class TopicDto {

    private Long id;
    private String name;

    public TopicDto() {
    }

    public TopicDto(TopicModel topicModel) {
        this.id = topicModel.getId();
        this.name = topicModel.getName();
    }

    public TopicModel toEntity() {
        return new TopicModel(this);
    }
}
