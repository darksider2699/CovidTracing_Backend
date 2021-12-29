package com.example.backend.models.user;

import com.example.backend.models.medical_information.DailyCheckin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "medical_user_information",
        uniqueConstraints = {

        })
@Getter
@Setter
public class MedicalUserInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToOne(targetEntity = User.class)
    private User user;
    @JsonIgnore
    @OneToMany(targetEntity = DailyCheckin.class, mappedBy = "medicalUserInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DailyCheckin> dailyCheckinInformationList ;

    private int vaccinatedStatus; //0: none, 1: 1 shot, 2: 2 shot
    private Date lastVaccinatedShot;
    private Date lastCovidTest;
    private int status; // Covid status - 0 = safe, 1 = F1, 2 = F2
    private Date lastCheckin;
    private Date lastCheckout;

}
