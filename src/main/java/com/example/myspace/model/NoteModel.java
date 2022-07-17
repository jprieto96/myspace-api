
package com.example.myspace.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "note")
public class NoteModel implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String text;

    @OneToMany(mappedBy = "noteModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteTopicModel> noteTopicModels;

}
