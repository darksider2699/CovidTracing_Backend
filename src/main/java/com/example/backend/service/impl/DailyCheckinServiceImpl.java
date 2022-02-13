package com.example.backend.service.impl;

import com.example.backend.repository.DailyCheckinRepository;
import com.example.backend.service.DailyCheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service("dailyCheckinService")
public class DailyCheckinServiceImpl implements DailyCheckinService {
    @Autowired
    DailyCheckinRepository dailyCheckinRepository;

    @Override
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(dailyCheckinRepository.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> search(String date) {
        LocalDate localDatePart = LocalDate.parse(date);
        return new ResponseEntity<>(dailyCheckinRepository.search(localDatePart), HttpStatus.OK);
    }

}
