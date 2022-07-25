package com.example.myspace.service;

import com.example.myspace.dto.ClientDto;
import com.example.myspace.exceptions.client.ClientException;
import com.example.myspace.model.ClientModel;

import java.util.Optional;

public interface ClientService {

    Optional<ClientDto> create(ClientDto clientDto) throws ClientException;
    Optional<ClientModel> findByEmail(String email);

}