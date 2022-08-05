package com.example.myspace.service;

import com.example.myspace.model.ClientModel;
import com.example.myspace.repository.ClientRepository;
import com.example.myspace.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService = new ClientServiceImpl();

    private AutoCloseable autoCloseable;
    private ClientModel clientIn;
    private ClientModel clientOut;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void create() {
    }

    @Test
    void findByEmail() {
    }

    @Test
    void findByUsername() {
    }

}