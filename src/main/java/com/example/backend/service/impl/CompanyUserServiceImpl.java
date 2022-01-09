package com.example.backend.service.impl;

import com.example.backend.models.job_title.JobTitle;
import com.example.backend.models.department.Department;
import com.example.backend.models.user.CompanyUserInformation;
import com.example.backend.payload.request.user.EditCompanyUserRequest;
import com.example.backend.payload.response.MessageResponse;
import com.example.backend.repository.CompanyUserRepository;
import com.example.backend.repository.DepartmentRepository;
import com.example.backend.repository.JobTitleRepository;
import com.example.backend.service.CompanyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;

@Service("companyUserService")
public class CompanyUserServiceImpl implements CompanyUserService {
    @Autowired
    CompanyUserRepository companyUserRepository;
    @Autowired
    JobTitleRepository jobTitleRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);

    public ResponseEntity<?> editUser(EditCompanyUserRequest editCompanyUserRequest, Long id) {
        Optional<CompanyUserInformation> opUser = companyUserRepository.findById(id);
        if (!opUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CompanyUserInformation user = opUser.get();
        if (editCompanyUserRequest.getCompanyEmail() != null && !editCompanyUserRequest.getCompanyEmail().isEmpty() && EMAIL_REGEX.matcher(editCompanyUserRequest.getCompanyEmail()).matches()) {
            user.setCompanyEmail(editCompanyUserRequest.getCompanyEmail());
        }
        if (editCompanyUserRequest.getJobTitleRequest() != null) {
            JobTitle jobTitle = new JobTitle(editCompanyUserRequest.getJobTitleRequest().getName(), editCompanyUserRequest.getJobTitleRequest().getLevel());
            Optional<JobTitle> optJobTitle = jobTitleRepository.search(jobTitle.getName(), jobTitle.getLevel());
            if (optJobTitle.isPresent()) {
                jobTitle = optJobTitle.get();
                Set<CompanyUserInformation> tempUser = jobTitle.getUsers();
                tempUser.add(user);
                jobTitle.setUsers(tempUser);
            }
            jobTitleRepository.save(jobTitle);
            Set<JobTitle> tempJob = user.getJobTitles();
            tempJob.add(jobTitle);
            user.setJobTitles(tempJob);
        }
        if (editCompanyUserRequest.getDepartmentId() != null) {
            Optional<Department> optDeparment = departmentRepository.findById(editCompanyUserRequest.getDepartmentId());
            if (optDeparment.isPresent()) {
                user.setDepartment(optDeparment.get());
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        companyUserRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User company information updated successfully!"));
    }
}
