
package com.example.myspace.model;

import com.example.myspace.dto.NoteDto;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "note")
public class NoteModel implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String text;

    @ManyToOne
    private ClientModel clientModel;

    @OneToMany(mappedBy = "noteModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteTopicModel> noteTopicModels;

    public NoteModel() {

    }

    public NoteDto toDto() {
        return new NoteDto(this);
    }

    public List<NoteTopicModel> getNoteTopicModels() {
        return noteTopicModels == null ? new ArrayList<>() : noteTopicModels;
    }
}
