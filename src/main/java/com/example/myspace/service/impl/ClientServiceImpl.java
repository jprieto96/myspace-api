package com.example.myspace.service.impl;

import com.example.myspace.dao.ClientDao;
import com.example.myspace.dto.ClientDto;
import com.example.myspace.model.ClientModel;
import com.example.myspace.security.UserPrinciple;
import com.example.myspace.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class ClientServiceImpl implements UserDetailsService, ClientService {

    @Autowired
    private ClientDao clientDao;

    @Override
    public Optional<ClientDto> create(ClientDto clientDto) throws Exception {
        Optional<ClientModel> auxClient = clientDao.findByEmail(clientDto.getEmail());

        ValidationException e = null;
        if(tClient.getUsername().equals("")) {
            e = new InvalidEmptyNameException();
        } else if (auxClient.isPresent() && auxClient.get().isActive()) {
            e = new InvalidDniExistsException();
        } else if (!validatePassword(tClient.getPassword())) {
            e = new InvalidPasswordFormatException();
        }

        if (Optional.ofNullable(e).isPresent()) {
            log.warn("User creation has not passed validation rules: {}",
                    e.getMessage());
            throw e;
        }

        log.debug("User: {} has passed validation rules", tClient.getUsername());

        Client client;

        if(auxClient.isPresent()) {
            client = auxClient.get();
            client.setActive(true);
            client.setDni(tClient.getDni());
            client.setPassword(tClient.getPassword());
            client.setUsername(tClient.getUsername());
        } else {
            tClient.setActive(true);
            client = new Client(tClient);
        }

        Client clientSaved = repositoryClient.save(client);

        return clientSaved.toTransfer();
    }

    @Override
    public Optional<ClientModel> findByEmail(String email) {
        return clientDao.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<ClientModel> optionalClientModel = clientDao.findByEmail(email);
        if(!optionalClientModel.isPresent()) throw new UsernameNotFoundException("Invalid email or password.");

        return UserPrinciple.build(optionalClientModel.get());
    }

    public boolean validatePassword(String password) {
        if(password.isEmpty()) {
            return false;
        }

        byte[] decodedBytes = Base64.getDecoder().decode(password);
        String decodedString = new String(decodedBytes);
        String decryptedPassword = decodedString.substring(0, decodedString.length() - 9);
        if (decryptedPassword.length() < 8) {
            return false;
        }
        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,}$");
        Matcher matcher = pattern.matcher(decryptedPassword);
        return matcher.find();
    }

}
