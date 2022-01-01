package com.example.backend.service.impl;

import com.example.backend.models.medical_information.DailyCheckin;
import com.example.backend.repository.DailyCheckinRepository;
import com.example.backend.service.DailyCheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service("dailyCheckinService")
public class DailyCheckinServiceImpl implements DailyCheckinService {
    @Autowired
    DailyCheckinRepository dailyCheckinRepository;

    @Override
    public List<DailyCheckin> findAll() {
        return dailyCheckinRepository.findAll();
    }

    @Override
    public List<DailyCheckin> search(String date) {
        LocalDate localDatePart = LocalDate.parse(date);
        return dailyCheckinRepository.search(localDatePart);
    }

}
