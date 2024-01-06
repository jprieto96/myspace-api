package com.example.myspace.client;

import com.example.myspace.client.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<ClientDto> register(@RequestBody ClientDto clientDto) {
        try {
            Optional<ClientDto> newClientDtoOptional = clientService.create(clientDto);
            if (!newClientDtoOptional.isPresent()) {
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "Client not created");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(newClientDtoOptional.get());
        } catch (ClientException e) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

}
