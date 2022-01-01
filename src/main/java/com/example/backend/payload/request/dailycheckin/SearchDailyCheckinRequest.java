package com.example.backend.payload.request.dailycheckin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SearchDailyCheckinRequest {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateRecord;
    private boolean isComing;
    private boolean isAllowToCome;


    public SearchDailyCheckinRequest() {
    }

    public SearchDailyCheckinRequest(Date dateRecord, boolean isComing, boolean isAllowToCome) {
        this.dateRecord = dateRecord;
        this.isComing = isComing;
        this.isAllowToCome = isAllowToCome;
    }
}
