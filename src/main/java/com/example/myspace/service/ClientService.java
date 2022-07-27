package com.example.myspace.service;

import com.example.myspace.dto.ClientDto;
import com.example.myspace.exceptions.client.ClientException;

import java.util.Optional;

public interface ClientService {

    Optional<ClientDto> create(ClientDto clientDto) throws ClientException;
    Optional<ClientDto> findByEmail(String email);
    Optional<ClientDto> findByUsername(String username);

}