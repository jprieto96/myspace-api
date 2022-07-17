package com.example.myspace.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "topic")
public class TopicModel implements Serializable {

    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "topicModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteTopicModel> noteTopicModels;

}
