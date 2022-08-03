package com.example.myspace.dto;

import com.example.myspace.model.ClientModel;
import com.example.myspace.util.Constants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Base64;

@Data
public class ClientDto {

    private Long id;

    private String name;

    private String username;

    private String password;

    private String passwordSalt;

    private String email;

    private boolean isAdmin;

    private boolean active;

    public ClientDto() {
    }

    public ClientDto(ClientModel clientModel) {
        this.id = clientModel.getId();
        this.name = clientModel.getName();
        this.username = clientModel.getUsername();
        this.password = clientModel.getPassword();
        this.passwordSalt = clientModel.getPasswordSalt();
        this.email = clientModel.getEmail();
        this.active = clientModel.isActive();
        this.isAdmin = clientModel.isAdmin();
    }

    public ClientModel toModel() {
        return new ClientModel(this);
    }

    @JsonIgnore
    public String getEncodedPasswordSalt() {
        return new String(Base64.getEncoder().encode(getSalt().getBytes()));
    }

    private String getSalt() {
        if(password == null) return null;
        byte[] decodedBytes = Base64.getDecoder().decode(password);
        String decodedString = new String(decodedBytes);
        return decodedString.substring(decodedString.length() - Constants.SALT_SIZE);
    }

}
