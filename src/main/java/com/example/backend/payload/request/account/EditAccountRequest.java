package com.example.backend.payload.request.account;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
public class EditAccountRequest {
    @NotBlank
    String newPassword;
}
