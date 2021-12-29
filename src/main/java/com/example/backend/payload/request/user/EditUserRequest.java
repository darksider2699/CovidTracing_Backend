package com.example.backend.payload.request.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class EditUserRequest {
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
}
