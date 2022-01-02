package com.example.backend.models.medical_information;

import com.example.backend.models.user.MedicalUserInformation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(   uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "medical_user_information_id"})}
)
@Getter
@Setter
public class VaccineInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date; // date take vaccine

    private EVaccineType type;

    @ManyToOne(targetEntity = MedicalUserInformation.class)
    MedicalUserInformation medicalUserInformation;

    public VaccineInformation() {
    }

    public VaccineInformation(Date date, EVaccineType type, MedicalUserInformation medicalUserInformation) {
        this.date = date;
        this.type = type;
        this.medicalUserInformation = medicalUserInformation;
    }
}
