package com.example.backend.payload.request.medical;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TestResultRequest {
    private Long idUser;
    private Date dateRecord;
    private boolean isPositive;
}
