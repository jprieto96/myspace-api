package com.example.myspace.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientModel, Long>, JpaSpecificationExecutor<ClientModel> {

    Optional<ClientModel> findByEmail(String email);
    Optional<ClientModel> findByUsername(String username);

}
