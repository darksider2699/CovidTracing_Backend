package com.example.backend.payload.request.user;

import com.example.backend.payload.request.medical.VaccineRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicalUserRequest {
    private Integer status; // Covid status - 0 = safe, 1 = F1, 2 = F2
    private VaccineRequest vaccineRequest;
}
