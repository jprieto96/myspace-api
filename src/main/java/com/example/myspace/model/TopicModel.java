package com.example.myspace.model;

import com.example.myspace.dto.TopicDto;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "topic")
public class TopicModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "topicModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteTopicModel> noteTopicModels;

    public TopicModel() {
    }

    public TopicModel(TopicDto topicDto) {
        this.name = topicDto.getName();
    }

    public TopicDto toDto() {
        return new TopicDto(this);
    }
}
