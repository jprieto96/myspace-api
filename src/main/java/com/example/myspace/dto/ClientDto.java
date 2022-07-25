package com.example.myspace.dto;

import com.example.myspace.model.ClientModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Base64;

@Data
public class ClientDto {

    private Long id;

    private String name;

    private String username;

    private String password;

    private String email;

    private boolean active;

    private Long clientGroupId;

    public ClientDto() {
    }

    public ClientDto(ClientModel clientModel) {
        this.id = clientModel.getId();
        this.name = clientModel.getName();
        this.username = clientModel.getUsername();
        this.password = clientModel.getPassword();
        this.email = clientModel.getEmail();
        this.active = clientModel.isActive();
        this.clientGroupId = clientModel.getClientGroup() != null ? clientModel.getClientGroup().getId() : null;
    }

    public ClientModel toModel() {
        return new ClientModel(this);
    }

    @JsonIgnore
    public String getPasswordWithoutSalt() {
        byte[] decodedBytes = Base64.getDecoder().decode(password);
        String decodedString = new String(decodedBytes);
        return decodedString.substring(0, decodedString.length() - 6);
    }
}
