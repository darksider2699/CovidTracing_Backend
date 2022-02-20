package com.example.backend.models.medical_information;

import com.example.backend.models.user.MedicalUserInformation;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {})}
)
@Getter
@Setter
public class CovidCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToMany(fetch = FetchType.LAZY)
    private List<MedicalUserInformation> patientContact;

    Date dateRecord; // date turns out as a patient

    @OneToOne
    private MedicalUserInformation patient;

    private Integer status;

    public CovidCase() {
    }

    public CovidCase(MedicalUserInformation patient, List<MedicalUserInformation> patientContact, Date dateRecord, Integer status) {
        this.patient = patient;
        this.patientContact = patientContact;
        this.dateRecord = dateRecord;
        this.status = status;
    }


}
