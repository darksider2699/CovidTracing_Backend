package com.example.backend.payload.request.medical;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class AddDailyCheckinRequest {
    private boolean isComing;
    private boolean isAllowToCome;
    private @DateTimeFormat Date dateRecord;
}
