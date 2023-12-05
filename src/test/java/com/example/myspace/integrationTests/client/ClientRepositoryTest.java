package com.example.myspace.integrationTests.client;

import com.example.myspace.client.ClientModel;
import com.example.myspace.client.ClientRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    private static ClientModel clientIn;
    private static boolean setUpIsDone = false;

    @BeforeEach
    void setUp() {
        if (setUpIsDone) {
            return;
        }

        clientIn = getClientIn();
        clientRepository.save(clientIn);
        setUpIsDone = true;
    }

    @Test
    @DisplayName("Find client by email")
    void findClientByEmailOk() {
        try {
            ClientModel clientOut = clientRepository.findByEmail(clientIn.getEmail()).orElse(null);
            Assertions.assertNotNull(clientOut);
            Assertions.assertEquals(clientOut.getId(), 1);
            Assertions.assertTrue(clientOut.isActive());
            Assertions.assertEquals(clientIn.getName(), clientOut.getName());
            Assertions.assertEquals(clientIn.getUsername(), clientOut.getUsername());
            Assertions.assertEquals(clientIn.getPassword(), clientOut.getPassword());
            Assertions.assertEquals(clientIn.getPasswordSalt(), clientOut.getPasswordSalt());
            Assertions.assertEquals(clientIn.getEmail(), clientOut.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Find client by non existent email")
    void findClientByNonExistentEmail() {
        try {
            ClientModel clientOut = clientRepository.findByEmail("").orElse(null);
            Assertions.assertNull(clientOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Find client by null email")
    void findClientByNullEmail() {
        try {
            ClientModel clientOut = clientRepository.findByEmail(null).orElse(null);
            Assertions.assertNull(clientOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Find client by username")
    void findClientByUsernameOk() {
        try {
            ClientModel clientOut = clientRepository.findByUsername(clientIn.getUsername()).orElse(null);
            Assertions.assertNotNull(clientOut);
            Assertions.assertEquals(clientOut.getId(), 1);
            Assertions.assertTrue(clientOut.isActive());
            Assertions.assertEquals(clientIn.getName(), clientOut.getName());
            Assertions.assertEquals(clientIn.getUsername(), clientOut.getUsername());
            Assertions.assertEquals(clientIn.getPassword(), clientOut.getPassword());
            Assertions.assertEquals(clientIn.getPasswordSalt(), clientOut.getPasswordSalt());
            Assertions.assertEquals(clientIn.getEmail(), clientOut.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Find client by non existent username")
    void findClientByNonExistentUsername() {
        try {
            ClientModel clientOut = clientRepository.findByUsername("").orElse(null);
            Assertions.assertNull(clientOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Find client by null username")
    void findClientByNullUsername() {
        try {
            ClientModel clientOut = clientRepository.findByUsername(null).orElse(null);
            Assertions.assertNull(clientOut);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ClientModel getClientIn() {
        ClientModel clientIn = new ClientModel();
        clientIn.setActive(true);
        clientIn.setName("Test");
        clientIn.setUsername("test");
        clientIn.setPassword("Sm9zZTEyMzRAcmU1JSY="); // Jose1234 + Salt(@re5%&)
        clientIn.setPasswordSalt("QHJlNSUm"); // @re5%&
        clientIn.setEmail("test@mail.com");
        clientIn.setAdmin(false);
        return clientIn;
    }

}
