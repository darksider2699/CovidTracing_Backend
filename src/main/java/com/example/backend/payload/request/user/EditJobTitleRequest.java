package com.example.backend.payload.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditJobTitleRequest {
    private String name;
    private Integer level;
}
