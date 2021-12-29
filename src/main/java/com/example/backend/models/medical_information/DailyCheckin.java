package com.example.backend.models.medical_information;

import com.example.backend.models.department.Department;
import com.example.backend.models.user.MedicalUserInformation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(   uniqueConstraints = {@UniqueConstraint(columnNames = {"dateRecord", "medical_user_information_id"})}
)
@Getter
@Setter
public class DailyCheckin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateRecord;

    @ManyToOne(targetEntity = MedicalUserInformation.class)
    MedicalUserInformation medicalUserInformation;

    private boolean isComing;

    private boolean isAllowToCome;

    public DailyCheckin() {
    }

    public DailyCheckin(Date dateRecord, MedicalUserInformation medicalUserInformation, boolean isComing, boolean isAllowToCome) {
        this.dateRecord = dateRecord;
        this.medicalUserInformation = medicalUserInformation;
        this.isComing = isComing;
        this.isAllowToCome = isAllowToCome;
    }
}
