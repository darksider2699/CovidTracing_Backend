package com.example.backend.payload.request.user;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
@Getter
@Setter
public class EditCompanyUserRequest {
    @Size(max = 50)
    @Email
    private String companyEmail;
    private Long departmentId;
    private EditJobTitleRequest jobTitleRequest;
}
