package com.example.myspace.model;

import com.example.myspace.dto.ClientDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "client")
@NoArgsConstructor
public class ClientModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean active;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.EAGER)
    private ClientGroupModel clientGroup;

    public ClientModel(ClientDto clientDto) {
        this.id = clientDto.getId();
        this.name = clientDto.getName();
        this.username = clientDto.getUsername();
        this.password = clientDto.getPassword();
        this.email = clientDto.getEmail();
        this.active = clientDto.isActive();
    }

    public ClientDto toDto() {
        return new ClientDto();
    }

}
