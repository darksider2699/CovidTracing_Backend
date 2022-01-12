package com.example.backend.models.medical_information;

import com.example.backend.models.user.MedicalUserInformation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "daily_checkout", uniqueConstraints = {@UniqueConstraint(columnNames = {"dateRecord", "medical_user_information_id"})}
)
@Getter
@Setter
public class DailyCheckout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateRecord;

    @JsonIgnoreProperties({"dailyCheckouts", "lastCheckout"})
    @ManyToOne(targetEntity = MedicalUserInformation.class)
    private MedicalUserInformation medicalUserInformation;

    @JsonIgnoreProperties({"dailyCheckouts", "lastCheckout"})
    @ManyToMany(fetch = FetchType.LAZY)
    private List<MedicalUserInformation> contact;
}
