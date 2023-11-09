package com.example.myspace.topic;

import com.example.myspace.topic.TopicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<TopicModel, Long>, JpaSpecificationExecutor<TopicModel> {
    Optional<TopicModel> findById(Long id);
    Optional<TopicModel> findByName(String name);

    @Query("SELECT t from TopicModel t where t.id NOT IN (select nt.topicModel from NoteTopicModel nt)")
    List<TopicModel> getTopicsToDelete(Long noteId);
}
