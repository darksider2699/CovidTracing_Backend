package com.example.backend.service;

import com.example.backend.payload.request.user.EditJobTitleRequest;
import com.example.backend.payload.request.user.EditUserRequest;
import org.springframework.http.ResponseEntity;

public interface JobTitleService {
    ResponseEntity<?> addJobTitle(EditJobTitleRequest editJobTitleRequest);
}
