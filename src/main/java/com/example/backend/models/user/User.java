package com.example.backend.models.user;

import com.example.backend.models.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = Account.class, cascade = CascadeType.ALL)
    private Account account;

    @JsonIgnoreProperties({"user"})
    @OneToOne(targetEntity = CompanyUserInformation.class, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private CompanyUserInformation companyUserInformation;

    @JsonIgnoreProperties("user")
    @JsonIgnore
    @OneToOne(targetEntity = MedicalUserInformation.class, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private MedicalUserInformation medicalUserInformation;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String contactAddress;

    private String phoneNumber;

    @Size(max = 50)
    @Email
    private String personalEmail;

    private Boolean gender; // 0 = male, 1 = female

    private String identityCard;

    public User() {

    }

    public User(Account account, String firstName, String lastName, Date dateOfBirth, String contactAddress, String phoneNumber, String personalEmail, Boolean gender, String identityCard, CompanyUserInformation companyUserInformation) {
        this.account = account;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.contactAddress = contactAddress;
        this.phoneNumber = phoneNumber;
        this.personalEmail = personalEmail;
        this.gender = gender;
        this.identityCard = identityCard;
        this.companyUserInformation = companyUserInformation;
    }
}