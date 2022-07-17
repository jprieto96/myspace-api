package com.example.myspace.dao;

import com.example.myspace.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ClientDao extends JpaRepository<ClientModel, Long>, JpaSpecificationExecutor<ClientModel> {
    Optional<ClientModel> findByEmail(String email);
}
