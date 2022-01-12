package com.example.backend.models.user;

import com.example.backend.models.medical_information.DailyCheckout;
import com.example.backend.models.medical_information.DailyCheckin;
import com.example.backend.models.medical_information.TestResult;
import com.example.backend.models.medical_information.VaccineInformation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table()
@Getter
@Setter
public class MedicalUserInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(targetEntity = User.class)
    private User user;

    @JsonIgnoreProperties("medicalUserInformation")
    @OneToMany(targetEntity = DailyCheckin.class, mappedBy = "medicalUserInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DailyCheckin> dailyCheckinInformationList ;

    @JsonIgnoreProperties({"medicalUserInformation", "contact"})
    @OneToMany(targetEntity = DailyCheckout.class, mappedBy = "medicalUserInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DailyCheckout> dailyCheckouts;

    @JsonIgnoreProperties("medicalUserInformation")
    @OneToMany(targetEntity = TestResult.class, mappedBy = "medicalUserInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<TestResult> testResultList;

    @JsonIgnoreProperties("medicalUserInformation")
    @OneToMany(targetEntity = VaccineInformation.class, mappedBy = "medicalUserInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<VaccineInformation> vaccineInformations;

    private Integer vaccinatedStatus; //0: none, 1: 1 shot, 2: 2 shot
    private Date lastVaccinatedShot;
    private Date lastCovidTest;
    private Integer status; // Covid status - 0 = safe, 1 = F1, 2 = F2

    @OneToOne
    @JsonIgnoreProperties("medicalUserInformation")
    private DailyCheckin lastCheckin;

    @OneToOne
    @JsonIgnoreProperties({"medicalUserInformation", "contact"})
    private DailyCheckout lastCheckout;

}
