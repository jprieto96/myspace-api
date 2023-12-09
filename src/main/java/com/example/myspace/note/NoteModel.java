
package com.example.myspace.note;

import com.example.myspace.client.ClientModel;
import com.example.myspace.notetopic.NoteTopicModel;
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

    @Column(nullable = false)
    private boolean active;

    @ManyToOne
    private ClientModel clientModel;

    @OneToMany(mappedBy = "noteModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteTopicModel> noteTopicModels;

    public NoteDto toDto() {
        return new NoteDto(this);
    }

    public List<NoteTopicModel> getNoteTopicModels() {
        return noteTopicModels == null ? new ArrayList<>() : noteTopicModels;
    }
}
