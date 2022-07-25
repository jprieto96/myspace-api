package com.example.myspace.controller;

import com.example.myspace.dto.ClientDto;
import com.example.myspace.exceptions.client.ValidationException;
import com.example.myspace.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<?> createClient(@RequestBody ClientDto clientDto) throws ValidationException {
        ClientDto newClientDto = clientService.create(clientDto).get();
        return ResponseEntity.status(HttpStatus.OK).body(newClientDto);
    }

}
