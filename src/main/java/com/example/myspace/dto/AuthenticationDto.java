package com.example.myspace.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Base64;

@Data
public class AuthenticationDto {

    private String username;

    private String password;

    @JsonIgnore
    public String getPasswordWithoutSalt() {
        byte[] decodedBytes = Base64.getDecoder().decode(password);
        String decodedString = new String(decodedBytes);
        return decodedString.substring(0, decodedString.length() - 6);
    }

}
