package com.example.backend.payload.request.medical;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AddDailyCheckoutRequest {
    private @DateTimeFormat
    Date dateRecord;

    List<Long> listIdContactToday;


}
