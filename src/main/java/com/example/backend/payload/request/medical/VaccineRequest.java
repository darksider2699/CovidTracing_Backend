package com.example.backend.payload.request.medical;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class VaccineRequest {
    private Date date; // date take vaccine

    private Long type;
}
