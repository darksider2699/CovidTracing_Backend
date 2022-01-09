package com.example.backend.payload.response;

import com.example.backend.models.user.MedicalUserInformation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class GetAllMedicalUserInformationResponse {
    Integer numberOfUser;
    Integer finishCheckinAmount;
    List<MedicalUserInformation> medicalUserInformationList;

    public GetAllMedicalUserInformationResponse(Integer numberOfUser, Integer finishCheckinAmount, List<MedicalUserInformation> medicalUserInformationList) {
        this.numberOfUser = numberOfUser;
        this.finishCheckinAmount = finishCheckinAmount;
        this.medicalUserInformationList = medicalUserInformationList;
    }

    public GetAllMedicalUserInformationResponse() {
    }
}
