package com.example.myspace.integrationTests.client;

import com.example.myspace.client.ClientDto;
import com.example.myspace.client.ClientModel;
import com.example.myspace.client.ClientRepository;
import com.example.myspace.client.ClientService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    private static ClientModel clientIn;
    private static ClientModel clientOut;

    @BeforeAll
    static void beforeAll() {
        clientIn = getClientIn();
        clientOut = getClientOut();
    }

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();
    }

    @Test
    @DisplayName("Create non existent client")
    void createClientNonExistentOk() {
        try {
            ClientDto clientDto = clientService.create(clientIn.toDto()).orElse(null);
            Assertions.assertNotNull(clientDto);
            Assertions.assertTrue(clientDto.isActive());
            Assertions.assertFalse(clientDto.isAdmin());
            Assertions.assertEquals(clientOut.getName(), clientDto.getName());
            Assertions.assertEquals(clientOut.getUsername(), clientDto.getUsername());
            Assertions.assertEquals(clientOut.getPasswordSalt(), clientDto.getPasswordSalt());
            Assertions.assertEquals(clientOut.getEmail(), clientDto.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Create existent and non active client")
    void createExistentClientOk() {
        try {
            clientIn.setActive(false);
            clientIn.setAdmin(false);
            clientRepository.save(clientIn);
            ClientDto clientDto = clientService.create(clientIn.toDto()).orElse(null);
            Assertions.assertNotNull(clientDto);
            Assertions.assertTrue(clientDto.isActive());
            Assertions.assertFalse(clientDto.isAdmin());
            Assertions.assertEquals(clientOut.getName(), clientDto.getName());
            Assertions.assertEquals(clientOut.getUsername(), clientDto.getUsername());
            Assertions.assertEquals(clientOut.getPasswordSalt(), clientDto.getPasswordSalt());
            Assertions.assertEquals(clientOut.getEmail(), clientDto.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Create existent and active client")
    void createExistingAndActiveClient() {
        try {
            clientIn.setActive(true);
            clientIn.setAdmin(false);
            clientRepository.save(clientIn);
            clientService.create(clientIn.toDto());
        } catch (Exception e) {
            Assertions.assertEquals("Client already exists", e.getMessage());
        }
    }

    @Test
    @DisplayName("Create client with empty username")
    void createClientEmptyUsername() {
        try {
            clientIn.setUsername("");
            clientService.create(clientIn.toDto());
        } catch (Exception e) {
            Assertions.assertEquals("Empty username", e.getMessage());
        } finally {
            clientIn.setUsername("test");
        }
    }

    @Test
    @DisplayName("Create client with empty name")
    void createClientEmptyName() {
        try {
            clientIn.setName("");
            clientService.create(clientIn.toDto());
        } catch (Exception e) {
            Assertions.assertEquals("Empty name", e.getMessage());
        } finally {
            clientIn.setName("Test");
        }
    }

    @Test
    @DisplayName("Create client with wrong password format")
    void createClientWithWrongPasswordFormat() {
        try {
            clientIn.setPassword("aGVsbG93b3JsZA=="); // helloworld
            clientService.create(clientIn.toDto());
        } catch (Exception e) {
            Assertions.assertEquals("User password format is not valid", e.getMessage());
        } finally {
            clientIn.setPassword("Sm9zZTEyMzRAcmU1JSY="); // Jose1234 + Salt(@re5%&)
        }
    }

    @Test
    @DisplayName("Create client with wrong email format")
    void createClientWithWrongEmailFormat() {
        try {
            clientIn.setEmail("jose.com");
            clientService.create(clientIn.toDto());
        } catch (Exception e) {
            Assertions.assertEquals("User email format is not valid", e.getMessage());
        } finally {
            clientIn.setEmail("test@mail.com");
        }
    }

    @Test
    @DisplayName("Create null client")
    void createNullClient() {
        try {
            clientService.create(null);
        } catch (Exception e) {
            Assertions.assertEquals("Null client provided", e.getMessage());
        }
    }

    @Test
    @DisplayName("Find client by email")
    void findByEmailOk() {
        try {
            clientIn.setActive(true);
            clientRepository.save(clientIn);
            ClientDto clientDto = clientService.findByEmail(clientIn.getEmail()).orElse(null);
            Assertions.assertNotNull(clientDto);
            Assertions.assertTrue(clientDto.isActive());
            Assertions.assertFalse(clientDto.isAdmin());
            Assertions.assertEquals(clientOut.getName(), clientDto.getName());
            Assertions.assertEquals(clientOut.getUsername(), clientDto.getUsername());
            Assertions.assertEquals(clientOut.getPasswordSalt(), clientDto.getPasswordSalt());
            Assertions.assertEquals(clientOut.getEmail(), clientDto.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Find client by non existent email")
    void findByEmailNonExistentClient() {
        try {
            clientIn.setActive(true);
            clientRepository.save(clientIn);
            ClientDto clientDto = clientService.findByEmail("").orElse(null);
            Assertions.assertNull(clientDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Find client by null email")
    void findByNullEmail() {
        try {
            clientIn.setActive(true);
            clientRepository.save(clientIn);
            ClientDto clientDto = clientService.findByEmail(null).orElse(null);
            Assertions.assertNull(clientDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Find client by username")
    void findByUsernameOk() {
        try {
            clientIn.setActive(true);
            clientRepository.save(clientIn);
            ClientDto clientDto = clientService.findByUsername(clientIn.getUsername()).orElse(null);
            Assertions.assertNotNull(clientDto);
            Assertions.assertTrue(clientDto.isActive());
            Assertions.assertFalse(clientDto.isAdmin());
            Assertions.assertEquals(clientOut.getName(), clientDto.getName());
            Assertions.assertEquals(clientOut.getUsername(), clientDto.getUsername());
            Assertions.assertEquals(clientOut.getPasswordSalt(), clientDto.getPasswordSalt());
            Assertions.assertEquals(clientOut.getEmail(), clientDto.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Find client by non existent username")
    void findByUsernameNonExistentClient() {
        try {
            clientRepository.save(clientIn);
            ClientDto clientDto = clientService.findByUsername("").orElse(null);
            Assertions.assertNull(clientDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Find client by null username")
    void findByNullUsername() {
        try {
            clientRepository.save(clientIn);
            ClientDto clientDto = clientService.findByUsername(null).orElse(null);
            Assertions.assertNull(clientDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ClientModel getClientIn() {
        ClientModel clientIn = new ClientModel();
        clientIn.setName("Test");
        clientIn.setUsername("test");
        clientIn.setPassword("Sm9zZTEyMzRAcmU1JSY="); // Jose1234 + Salt(@re5%&)
        clientIn.setPasswordSalt("QHJlNSUm"); // @re5%&
        clientIn.setEmail("test@mail.com");
        return clientIn;
    }

    private static ClientModel getClientOut() {
        ClientModel clientOut = new ClientModel();
        clientOut.setActive(true);
        clientOut.setName("Test");
        clientOut.setUsername("test");
        clientOut.setPasswordSalt("QHJlNSUm"); // @re5%&
        clientOut.setEmail("test@mail.com");
        clientOut.setAdmin(false);
        return clientOut;
    }

}
