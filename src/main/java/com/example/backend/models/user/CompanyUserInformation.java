package com.example.backend.models.user;

import com.example.backend.models.job_title.JobTitle;
import com.example.backend.models.department.Department;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "company_user_information",
        uniqueConstraints = {

        })
@Getter
@Setter
public class CompanyUserInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnoreProperties({"companyUserInformation"})
    @OneToOne(targetEntity = User.class)
    private User user;

    @Size(max = 50)
    @Email
    private String companyEmail;

    @ManyToOne(targetEntity = Department.class)
    Department department;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "jobTitles_companyUserInformations",
            joinColumns = @JoinColumn(name = "companyUserInformation_id"),
            inverseJoinColumns = @JoinColumn(name = "jobTitle_id"))

    Set<JobTitle> jobTitles = new HashSet<>();

    public CompanyUserInformation() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompanyUserInformation)) return false;
        CompanyUserInformation that = (CompanyUserInformation) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
