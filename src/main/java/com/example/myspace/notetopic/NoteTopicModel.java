package com.example.myspace.notetopic;

import com.example.myspace.note.NoteModel;
import com.example.myspace.topic.TopicModel;
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