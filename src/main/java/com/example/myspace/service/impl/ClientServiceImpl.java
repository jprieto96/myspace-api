package com.example.myspace.service.impl;

import com.example.myspace.exceptions.client.*;
import com.example.myspace.repository.ClientRepository;
import com.example.myspace.dto.ClientDto;
import com.example.myspace.model.ClientModel;
import com.example.myspace.security.UserPrinciple;
import com.example.myspace.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class ClientServiceImpl implements UserDetailsService, ClientService {

    private static final Logger LOGGER = LogManager.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<ClientDto> create(ClientDto clientDto) throws ValidationException {
        Optional<ClientModel> auxClient = clientRepository.findByEmail(clientDto.getEmail());

        ValidationException e = null;
        if(clientDto.getUsername().equals("")) {
            e = new EmptyNameException();
        } else if (auxClient.isPresent() && auxClient.get().isActive()) {
            e = new ClientExistsException();
        } else if (!isPasswordValid(clientDto)) {
            e = new PasswordFormatException();
        } else if (!isEmailValid(clientDto.getEmail())) {
            e = new EmailFormatException();
        }

        if (Optional.ofNullable(e).isPresent()) {
            LOGGER.error("Client creation has not passed validation rules: {}",
                    e.getMessage());
            throw e;
        }

        LOGGER.debug("Client: {} has passed validation rules", clientDto.getUsername());

        ClientModel client = null;
        if(auxClient.isPresent()) {
            client = auxClient.get();
            client.setActive(true);
            client.setName(clientDto.getName());
            client.setPassword(passwordEncoder.encode(clientDto.getPassword()));
            client.setUsername(clientDto.getUsername());
        } else {
            clientDto.setActive(true);
            clientDto.setPassword(passwordEncoder.encode(clientDto.getPassword()));
            client = new ClientModel(clientDto);
        }

        ClientModel clientSaved = clientRepository.save(client);

        return Optional.ofNullable(clientSaved.toDto());
    }

    private boolean isEmailValid(String email) {
        String pattern = "^[\\p{L}0-9!#$%&'*+\\/=?^_`{|}~-][\\p{L}0-9.!#$%&'*+\\/=?^_`{|}~-]{0,63}@[\\p{L}0-9-]+(?:\\.[\\p{L}0-9-]{2,7})*$";
        return Pattern.matches(pattern, email);
    }

    @Override
    public Optional<ClientModel> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<ClientModel> optionalClientModel = clientRepository.findByEmail(email);
        if(!optionalClientModel.isPresent()) throw new UsernameNotFoundException("Invalid email or password.");

        return UserPrinciple.build(optionalClientModel.get());
    }

    public boolean isPasswordValid(ClientDto clientDto) {
        if(clientDto.getPassword().isEmpty()) {
            return false;
        }

        String decryptedPassword = clientDto.getPasswordWithoutSalt();
        if (decryptedPassword.length() < 8) {
            return false;
        }
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,}$");
        Matcher matcher = pattern.matcher(decryptedPassword);
        return matcher.find();
    }

}
