package com.example.myspace.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "note_topic")
public class NoteTopicModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private NoteModel noteModel;

    @ManyToOne
    private TopicModel topicModel;

}