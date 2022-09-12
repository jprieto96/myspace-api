package com.example.myspace.security;

import com.example.myspace.client.ClientRol;
import com.example.myspace.client.ClientModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


// Class used for JWT authentication (the attributes and the way in which the authorities collection is filled in can change)
@Data
public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String username;

    @JsonIgnore
    private String password;

    private String email;

    private boolean isAdmin;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(Long id, String name, String username, String password, String email, boolean isAdmin, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.isAdmin = isAdmin;
        this.authorities = authorities;
    }

    public static UserPrinciple build(ClientModel userModel) {

        // How the user is only going to have a Role (UserGroup), only one value is added to the authorities
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        if(userModel.isAdmin()) {
            authorities.add(new SimpleGrantedAuthority(ClientRol.ADMIN.name()));
        }
        else {
            authorities.add(new SimpleGrantedAuthority(ClientRol.USER.name()));
        }

        return new UserPrinciple(
                userModel.getId(),
                userModel.getName(),
                userModel.getUsername(),
                userModel.getPassword(),
                userModel.getEmail(),
                userModel.isAdmin(),
                authorities
        );
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(getId(), user.getId());
    }
}

