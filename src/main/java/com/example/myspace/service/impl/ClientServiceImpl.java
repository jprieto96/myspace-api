package com.example.myspace.service.impl;

import com.example.myspace.exceptions.client.*;
import com.example.myspace.repository.ClientRepository;
import com.example.myspace.dto.ClientDto;
import com.example.myspace.model.ClientModel;
import com.example.myspace.security.UserPrinciple;
import com.example.myspace.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@Log4j2
public class ClientServiceImpl implements UserDetailsService, ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Optional<ClientDto> create(ClientDto clientDto) throws ClientException {
        Optional<ClientModel> auxClientByEmail = clientRepository.findByEmail(clientDto.getEmail());
        Optional<ClientModel> auxClientByUsername = clientRepository.findByUsername(clientDto.getUsername());

        ClientException e = null;
        if (clientDto == null) {
            e = new ClientNotFoundException();
        } else if(clientDto.getUsername() == null || clientDto.getUsername().equals("")) {
            e = new EmptyUserNameException();
        } else if (clientDto.getName() == null || clientDto.getName().equals("")) {
            e = new EmptyNameException();
        } else if ((auxClientByEmail.isPresent() && auxClientByEmail.get().isActive()) || auxClientByUsername.isPresent()) {
            e = new ClientExistsException();
        } else if (!isPasswordValid(clientDto)) {
            e = new PasswordFormatException();
        } else if (!isEmailValid(clientDto.getEmail())) {
            e = new EmailFormatException();
        }

        if (Optional.ofNullable(e).isPresent()) {
            log.error("Client creation has not passed validation rules: {}",
                    e.getMessage());
            throw e;
        }

        log.debug("Client: {} has passed validation rules", clientDto.getUsername());

        ClientModel client = null;
        if(auxClientByEmail.isPresent()) {
            client = auxClientByEmail.get();
            client.setActive(true);
            client.setName(clientDto.getName());
            client.setPassword(bCryptPasswordEncoder.encode(clientDto.getPasswordWithoutSalt()));
            client.setUsername(clientDto.getUsername());
        } else {
            clientDto.setActive(true);
            clientDto.setPassword(bCryptPasswordEncoder.encode(clientDto.getPasswordWithoutSalt()));
            client = new ClientModel(clientDto);
        }

        ClientModel clientSaved = clientRepository.save(client);

        return Optional.ofNullable(clientSaved.toDto());
    }

    private boolean isEmailValid(String email) {
        if(email == null) return false;
        String pattern = "^[\\p{L}0-9!#$%&'*+\\/=?^_`{|}~-][\\p{L}0-9.!#$%&'*+\\/=?^_`{|}~-]{0,63}@[\\p{L}0-9-]+(?:\\.[\\p{L}0-9-]{2,7})*$";
        return Pattern.matches(pattern, email);
    }

    @Override
    public Optional<ClientModel> findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ClientModel> optionalClientModel = clientRepository.findByEmail(username);
        if(!optionalClientModel.isPresent()) throw new UsernameNotFoundException("Invalid email or password.");

        return UserPrinciple.build(optionalClientModel.get());
    }

    public boolean isPasswordValid(ClientDto clientDto) {
        if(clientDto.getPassword() == null || clientDto.getPassword().isEmpty()) {
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
