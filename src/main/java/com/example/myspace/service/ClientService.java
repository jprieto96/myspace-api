package com.example.myspace.service;

import com.example.myspace.dto.ClientDto;

import java.util.Optional;

public interface ClientService {

    Optional<ClientDto> create(ClientDto clientDto) throws Exception;
    Optional<ClientDto> findByEmail(String email);

}