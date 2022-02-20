package com.example.backend.payload.request.medical;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CovidCaseRequest {
    private Long id;
    private Integer covidStatus; // 0: F0, 1: F1, 2: F2
    private Date dateRecord;
}
