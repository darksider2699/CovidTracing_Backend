package com.example.backend.models;

import com.example.backend.models.role.Role;
import com.example.backend.models.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
        })
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 120)
    private String password;

    @JsonIgnore
    @OneToOne(targetEntity = User.class, mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "account_roles",
            joinColumns = @JoinColumn(name = "accounts_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles = new HashSet<>();

    public Account() {

    }

    public Account(String username, String password, User user) {
        this.user = user;
        this.username = username;
        this.password = password;
    }
}
