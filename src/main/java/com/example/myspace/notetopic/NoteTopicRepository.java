package com.example.myspace.notetopic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface NoteTopicRepository extends JpaRepository<NoteTopicModel, Long>, JpaSpecificationExecutor<NoteTopicModel> {

    void deleteNoteTopicModelByNoteModelId(Long id);

}
