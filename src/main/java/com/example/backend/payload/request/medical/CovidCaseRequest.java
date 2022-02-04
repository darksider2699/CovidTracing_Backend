package com.example.backend.payload.request.medical;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CovidCaseRequest {
    private Long id;
    private int covidStatus; // 0: F0, 1: F1, 2: F2
}
