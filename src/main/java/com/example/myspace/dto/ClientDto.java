package com.example.myspace.dto;

import com.example.myspace.model.ClientModel;
import lombok.Data;

@Data
public class ClientDto {

    private Long id;

    private String name;

    private String password;

    private String email;

    private boolean active;

    private Long clientGroupId;

    public ClientModel toModel() {
        return new ClientModel(this.id, this.name, this.password, this.email, this.active);
    }

}
