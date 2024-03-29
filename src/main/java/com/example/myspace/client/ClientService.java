package com.example.myspace.client;


import com.example.myspace.client.exceptions.ClientException;

import java.util.Optional;

public interface ClientService {

    Optional<ClientDto> create(ClientDto clientDto) throws ClientException;
    Optional<ClientDto> findByEmail(String email);
    Optional<ClientDto> findByUsername(String username);

}