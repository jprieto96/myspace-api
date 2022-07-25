package com.example.myspace.dto;

import lombok.Data;

import java.util.Base64;

@Data
public class AuthenticationDto {

    private String email;

    private String password;

    public String getPasswordWithoutSalt() {
        byte[] decodedBytes = Base64.getDecoder().decode(password);
        String decodedString = new String(decodedBytes);
        return decodedString.substring(0, decodedString.length() - 6);
    }

}
