package com.example.myspace.model;

import com.example.myspace.dto.ClientDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Table(name = "client")
public class ClientModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String passwordSalt;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean isAdmin;

    @OneToMany(mappedBy = "clientModel")
    private List<NoteModel> noteList;

    @Column(nullable = false)
    private boolean active;

    public ClientModel(ClientDto clientDto) {
        this.id = clientDto.getId();
        this.name = clientDto.getName();
        this.username = clientDto.getUsername();
        this.password = clientDto.getPassword();
        this.passwordSalt = clientDto.getPasswordSalt();
        this.email = clientDto.getEmail();
        this.isAdmin = clientDto.isAdmin();
        this.active = clientDto.isActive();
    }

    public ClientModel() {

    }

    public ClientDto toDto() {
        return new ClientDto(this);
    }

}
