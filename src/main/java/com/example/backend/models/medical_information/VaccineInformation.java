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

    @OneToOne
    private VaccineType type;

    @ManyToOne(targetEntity = MedicalUserInformation.class)
    MedicalUserInformation medicalUserInformation;

    public VaccineInformation() {
    }

    public VaccineInformation(Date date, VaccineType type, MedicalUserInformation medicalUserInformation) {
        this.date = date;
        this.type = type;
        this.medicalUserInformation = medicalUserInformation;
    }
}
