package com.example.myspace.notetopic;

import com.example.myspace.notetopic.NoteTopicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NoteTopicRepository extends JpaRepository<NoteTopicModel, Long>, JpaSpecificationExecutor<NoteTopicModel> {
}
