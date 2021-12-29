package com.example.backend.models.department;

import com.example.backend.models.role.ERole;
import com.example.backend.models.user.CompanyUserInformation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "department", schema = "testdb")
@Getter
@Setter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EDepartment name;

    @OneToMany(targetEntity = CompanyUserInformation.class, mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<CompanyUserInformation> users;
}
