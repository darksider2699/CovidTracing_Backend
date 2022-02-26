package com.example.backend.service.impl;

import com.example.backend.models.medical_information.EVaccineType;
import com.example.backend.models.medical_information.VaccineInformation;
import com.example.backend.models.medical_information.VaccineType;
import com.example.backend.models.user.MedicalUserInformation;
import com.example.backend.payload.request.medical.VaccineRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.MedicalUserRepository;
import com.example.backend.repository.VaccineInformationRepository;
import com.example.backend.repository.VaccineRepository;
import com.example.backend.service.VaccineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service("vaccineService")
public class VaccineServiceImpl implements VaccineService {
    @Autowired
    VaccineInformationRepository vaccineInformationRepository;
    @Autowired
    VaccineRepository vaccineRepository;
    @Autowired
    MedicalUserRepository medicalUserRepository;

    @Override
    public ResponseEntity<?> addNewVaccineShot(VaccineRequest vaccineRequest, Long id) {
        Optional<MedicalUserInformation> optUser = medicalUserRepository.findById(id);
        if (!optUser.isPresent()) {
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }
        MedicalUserInformation medicalUserInformation = optUser.get();
        Set<VaccineInformation> vaccineShotList = medicalUserInformation.getVaccineInformations();
        Optional<VaccineType> vaccineType = vaccineRepository.findById(vaccineRequest.getType());
        if (!vaccineType.isPresent()) {
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }
        VaccineInformation newVaccineShot = new VaccineInformation(vaccineRequest.getDate(), vaccineType.get(), medicalUserInformation);
        vaccineInformationRepository.save(newVaccineShot);
        return ResponseEntity.ok(new MessageResponse("Vaccine information has just been updated"));
    }

    @Override
    public ResponseEntity<?> getAllVaccineType() {
        return new ResponseEntity<>(vaccineRepository.findAll(), HttpStatus.OK);

    }


    @Override
    public ResponseEntity<?> deleteVaccineShot(Long id) {
        Optional<VaccineInformation> optVaccineInformation = vaccineInformationRepository.findById(id);
        if(!optVaccineInformation.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        vaccineInformationRepository.delete(optVaccineInformation.get());
        return ResponseEntity.ok(new MessageResponse("Vaccine information has just been deleted"));
    }
}
