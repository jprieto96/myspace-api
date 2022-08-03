package com.example.myspace.dto;

public enum UserRol {

    ADMIN("ADMIN"), USER("USER");

    private String rol;

    UserRol(String rol) {
        this.rol = rol;
    }
}
