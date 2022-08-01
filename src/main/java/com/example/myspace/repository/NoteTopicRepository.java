package com.example.myspace.repository;

import com.example.myspace.model.NoteTopicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface NoteTopicRepository extends JpaRepository<NoteTopicModel, Long>, JpaSpecificationExecutor<NoteTopicModel> {
}
