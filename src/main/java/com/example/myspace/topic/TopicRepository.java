package com.example.myspace.topic;

import com.example.myspace.topic.TopicModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<TopicModel, Long>, JpaSpecificationExecutor<TopicModel> {
    Optional<TopicModel> findById(Long id);
    Optional<TopicModel> findByName(String name);
}
