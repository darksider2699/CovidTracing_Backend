package com.example.backend.models.department;

import com.example.backend.models.user.CompanyUserInformation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table()
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EDepartment name;

    @JsonIgnore
    @OneToMany(targetEntity = CompanyUserInformation.class, mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<CompanyUserInformation> users;
}
