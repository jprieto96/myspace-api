package com.example.myspace.client;

import com.example.myspace.exceptions.client.*;
import com.example.myspace.client.ClientRepository;
import com.example.myspace.client.ClientDto;
import com.example.myspace.client.ClientModel;
import com.example.myspace.security.UserPrinciple;
import com.example.myspace.client.ClientService;
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
            client.setAdmin(false);
            client.setName(clientDto.getName());
            client.setPasswordSalt(clientDto.getEncodedPasswordSalt());
            client.setPassword(bCryptPasswordEncoder.encode(clientDto.getPassword()));
            client.setUsername(clientDto.getUsername());
        } else {
            clientDto.setActive(true);
            clientDto.setAdmin(false);
            clientDto.setPasswordSalt(clientDto.getEncodedPasswordSalt());
            clientDto.setPassword(bCryptPasswordEncoder.encode(clientDto.getPassword()));
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
    public Optional<ClientDto> findByEmail(String email) {
        Optional<ClientModel> optionalClientModel = clientRepository.findByEmail(email);
        if(!optionalClientModel.isPresent()) return Optional.empty();
        return Optional.ofNullable(optionalClientModel.get().toDto());
    }

    @Override
    public Optional<ClientDto> findByUsername(String username) {
        Optional<ClientModel> optionalClientModel = clientRepository.findByUsername(username);
        if(!optionalClientModel.isPresent()) return Optional.empty();
        return Optional.ofNullable(optionalClientModel.get().toDto());
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ClientModel> optionalClientModel = clientRepository.findByUsername(username);
        if(!optionalClientModel.isPresent()) throw new UsernameNotFoundException("Invalid username.");

        return UserPrinciple.build(optionalClientModel.get());
    }

    private boolean isPasswordValid(ClientDto clientDto) {
        if(clientDto.getPassword() == null || clientDto.getPassword().isEmpty()) {
            return false;
        }

        byte[] decodedBytes = Base64.getDecoder().decode(clientDto.getPassword());
        String decodedString = new String(decodedBytes);
        String decryptedPassword = decodedString.substring(0, decodedString.length() - 6);

        if (decryptedPassword.length() < 8) {
            return false;
        }

        Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,}$");
        Matcher matcher = pattern.matcher(decryptedPassword);
        return matcher.find();
    }

}
