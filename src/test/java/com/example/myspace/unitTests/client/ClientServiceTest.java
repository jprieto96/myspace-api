package com.example.myspace.unitTests.client;

import com.example.myspace.client.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService = new ClientServiceImpl();

    private AutoCloseable autoCloseable;
    private ClientModel clientIn;
    private ClientModel clientOut;

    @BeforeEach
    void setUpInAndOutClients() {
        autoCloseable = MockitoAnnotations.openMocks(this);

        clientIn = new ClientModel();
        clientIn.setName("Test");
        clientIn.setUsername("test");
        clientIn.setPassword("Sm9zZTEyMzRAcmU1JSY="); // Jose1234 + Salt(@re5%&)
        clientIn.setPasswordSalt("QHJlNSUm"); // @re5%&
        clientIn.setEmail("test@mail.com");

        clientOut = new ClientModel();
        clientOut.setId(1L);
        clientOut.setActive(true);
        clientOut.setName("Test");
        clientOut.setUsername("test");
        clientOut.setPassword("$2a$12$e/3zzZhyu1EdU3k6rY0GReNl9lF.qtSse8PZYS2yWD5noIur0LzF."); // Jose1234 + Salt(@re5%&)
        clientOut.setPasswordSalt("QHJlNSUm"); // @re5%&
        clientOut.setEmail("test@mail.com");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    @DisplayName("Create non existent client")
    void createClientNonExistentOk() {
        when(clientRepository.findByEmail(clientIn.getEmail())).thenReturn(Optional.empty());
        when(clientRepository.findByUsername(clientIn.getUsername())).thenReturn(Optional.empty());
        when(clientRepository.save(any())).thenReturn(clientOut);

        try {
            ClientDto clientDto = clientService.create(clientIn.toDto()).orElse(null);
            Assertions.assertNotNull(clientDto);
            Assertions.assertEquals(1, clientDto.getId());
            Assertions.assertTrue(clientDto.isActive());
            Assertions.assertEquals(clientOut.getName(), clientDto.getName());
            Assertions.assertEquals(clientOut.getUsername(), clientDto.getUsername());
            Assertions.assertEquals(clientOut.getPassword(), clientDto.getPassword());
            Assertions.assertEquals(clientOut.getPasswordSalt(), clientDto.getPasswordSalt());
            Assertions.assertEquals(clientOut.getEmail(), clientDto.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Create existent and non active client")
    void createExistentClientOk() {
        when(clientRepository.findByEmail(clientIn.getEmail())).thenReturn(Optional.of(clientIn));
        when(clientRepository.findByUsername(clientIn.getUsername())).thenReturn(Optional.of(clientIn));
        when(clientRepository.save(any())).thenReturn(clientOut);

        try {
            ClientDto clientDto = clientService.create(clientIn.toDto()).orElse(null);
            Assertions.assertNotNull(clientDto);
            Assertions.assertEquals(1, clientDto.getId());
            Assertions.assertTrue(clientDto.isActive());
            Assertions.assertEquals(clientOut.getName(), clientDto.getName());
            Assertions.assertEquals(clientOut.getUsername(), clientDto.getUsername());
            Assertions.assertEquals(clientOut.getPassword(), clientDto.getPassword());
            Assertions.assertEquals(clientOut.getPasswordSalt(), clientDto.getPasswordSalt());
            Assertions.assertEquals(clientOut.getEmail(), clientDto.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Create client with empty username")
    void createClientEmptyUsername() {
        when(clientRepository.findByEmail(clientIn.getEmail())).thenReturn(Optional.empty());
        when(clientRepository.findByUsername(clientIn.getUsername())).thenReturn(Optional.empty());

        try {
            clientIn.setUsername("");
            clientService.create(clientIn.toDto());
        } catch (Exception e) {
            Assertions.assertEquals("Empty username", e.getMessage());
        }
    }

    @Test
    @DisplayName("Create client with empty name")
    void createClientEmptyName() {
        when(clientRepository.findByEmail(clientIn.getEmail())).thenReturn(Optional.empty());
        when(clientRepository.findByUsername(clientIn.getUsername())).thenReturn(Optional.empty());

        try {
            clientIn.setName("");
            clientService.create(clientIn.toDto());
        } catch (Exception e) {
            Assertions.assertEquals("Empty name", e.getMessage());
        }
    }

    @Test
    @DisplayName("Create existent and active client")
    void createExistingAndActiveClient() {
        when(clientRepository.findByEmail(clientIn.getEmail())).thenReturn(Optional.of(clientOut));
        when(clientRepository.findByUsername(clientIn.getUsername())).thenReturn(Optional.of(clientOut));

        try {
            clientIn.setActive(true);
            clientService.create(clientIn.toDto());
        } catch (Exception e) {
            Assertions.assertEquals("Client already exists", e.getMessage());
        }
    }

    @Test
    @DisplayName("Create client with wrong password format")
    void createClientWithWrongPasswordFormat() {
        when(clientRepository.findByEmail(clientIn.getEmail())).thenReturn(Optional.empty());
        when(clientRepository.findByUsername(clientIn.getUsername())).thenReturn(Optional.empty());

        try {
            clientIn.setPassword("aGVsbG93b3JsZA=="); // helloworld
            clientService.create(clientIn.toDto());
        } catch (Exception e) {
            Assertions.assertEquals("User password format is not valid", e.getMessage());
        }
    }

    @Test
    @DisplayName("Create client with wrong email format")
    void createClientWithWrongEmailFormat() {
        when(clientRepository.findByEmail(clientIn.getEmail())).thenReturn(Optional.empty());
        when(clientRepository.findByUsername(clientIn.getUsername())).thenReturn(Optional.empty());

        try {
            clientIn.setEmail("jose.com");
            clientService.create(clientIn.toDto());
        } catch (Exception e) {
            Assertions.assertEquals("User email format is not valid", e.getMessage());
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
        when(clientRepository.findByEmail(clientIn.getEmail())).thenReturn(Optional.ofNullable(clientOut));

        try {
            ClientDto clientDto = clientService.findByEmail(clientIn.getEmail()).orElse(null);
            Assertions.assertNotNull(clientDto);
            Assertions.assertEquals(1, clientDto.getId());
            Assertions.assertTrue(clientDto.isActive());
            Assertions.assertEquals(clientOut.getName(), clientDto.getName());
            Assertions.assertEquals(clientOut.getUsername(), clientDto.getUsername());
            Assertions.assertEquals(clientOut.getPassword(), clientDto.getPassword());
            Assertions.assertEquals(clientOut.getPasswordSalt(), clientDto.getPasswordSalt());
            Assertions.assertEquals(clientOut.getEmail(), clientDto.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Find client by non existent email")
    void findByEmailNonExistentClient() {
        when(clientRepository.findByEmail(clientIn.getEmail())).thenReturn(Optional.empty());

        try {
            ClientDto clientDto = clientService.findByEmail(clientIn.getEmail()).orElse(null);
            Assertions.assertNull(clientDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Find client by null email")
    void findByNullEmail() {
        when(clientRepository.findByEmail(null)).thenReturn(Optional.empty());

        try {
            ClientDto clientDto = clientService.findByEmail(null).orElse(null);
            Assertions.assertNull(clientDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Find client by username")
    void findByUsernameOk() {
        when(clientRepository.findByUsername(clientIn.getUsername())).thenReturn(Optional.ofNullable(clientOut));

        try {
            ClientDto clientDto = clientService.findByUsername(clientIn.getUsername()).orElse(null);
            Assertions.assertNotNull(clientDto);
            Assertions.assertEquals(1, clientDto.getId());
            Assertions.assertTrue(clientDto.isActive());
            Assertions.assertEquals(clientOut.getName(), clientDto.getName());
            Assertions.assertEquals(clientOut.getUsername(), clientDto.getUsername());
            Assertions.assertEquals(clientOut.getPassword(), clientDto.getPassword());
            Assertions.assertEquals(clientOut.getPasswordSalt(), clientDto.getPasswordSalt());
            Assertions.assertEquals(clientOut.getEmail(), clientDto.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Find client by non existent username")
    void findByUsernameNonExistentClient() {
        when(clientRepository.findByUsername(clientIn.getUsername())).thenReturn(Optional.empty());

        try {
            ClientDto clientDto = clientService.findByUsername(clientIn.getUsername()).orElse(null);
            Assertions.assertNull(clientDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Find client by null username")
    void findByNullUsername() {
        when(clientRepository.findByUsername(null)).thenReturn(Optional.empty());

        try {
            ClientDto clientDto = clientService.findByUsername(null).orElse(null);
            Assertions.assertNull(clientDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}