package com.example.backend.models.medical_information;

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
public class TestResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateRecord;

    @ManyToOne(targetEntity = MedicalUserInformation.class)
    MedicalUserInformation medicalUserInformation;

    private boolean isPositive;


    public TestResult() {
    }

    public TestResult(Date dateRecord, MedicalUserInformation medicalUserInformation, boolean isPositive) {
        this.dateRecord = dateRecord;
        this.medicalUserInformation = medicalUserInformation;
        this.isPositive = isPositive;
    }
}
