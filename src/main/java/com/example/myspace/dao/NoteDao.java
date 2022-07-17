package com.example.myspace.dao;

import com.example.myspace.model.NoteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface NoteDao extends JpaRepository<NoteModel, Long>, JpaSpecificationExecutor<NoteModel> {
    Optional<NoteModel> findById(Long id);
}
