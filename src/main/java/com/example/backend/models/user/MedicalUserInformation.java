package com.example.backend.models.user;

import com.example.backend.models.medical_information.DailyCheckout;
import com.example.backend.models.medical_information.DailyCheckin;
import com.example.backend.models.medical_information.TestResult;
import com.example.backend.models.medical_information.VaccineInformation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;
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

    @JsonIgnoreProperties({"medicalUserInformation"})
    @OneToMany(targetEntity = DailyCheckout.class, mappedBy = "medicalUserInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DailyCheckout> dailyCheckouts;

    @JsonIgnoreProperties("medicalUserInformation")
    @OneToMany(targetEntity = TestResult.class, mappedBy = "medicalUserInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<TestResult> testResultList;

    @JsonIgnoreProperties("medicalUserInformation")
    @OneToMany(targetEntity = VaccineInformation.class, mappedBy = "medicalUserInformation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<VaccineInformation> vaccineInformations;

    @ElementCollection
    private Set<Integer> contactRelevant;

    private Integer status; // Covid status - 0 = F0, 1 = F1, 2 = F2, 3 = Safe

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedicalUserInformation)) return false;
        MedicalUserInformation that = (MedicalUserInformation) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
