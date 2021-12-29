package com.example.backend.payload.request.dailycheckin;

import com.example.backend.models.user.MedicalUserInformation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
public class SearchDailyCheckinRequest {
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
