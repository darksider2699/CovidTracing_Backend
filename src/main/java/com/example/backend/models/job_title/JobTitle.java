package com.example.backend.models.job_title;

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
public class JobTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private String name;

    private Integer level;

    @JsonIgnore
    @ManyToMany(targetEntity = CompanyUserInformation.class, mappedBy = "jobTitles", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<CompanyUserInformation> users;

    public JobTitle() {
    }

    public JobTitle(String name, Integer level) {
        this.name = name;
        this.level = level;
    }
}