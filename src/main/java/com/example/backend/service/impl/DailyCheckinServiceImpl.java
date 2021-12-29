package com.example.backend.service.impl;

import com.example.backend.models.medical_information.DailyCheckin;
import com.example.backend.repository.DailyCheckinRepository;
import com.example.backend.service.DailyCheckinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("dailyCheckinService")
public class DailyCheckinServiceImpl implements DailyCheckinService {
    @Autowired
    DailyCheckinRepository dailyCheckinRepository;

    @Override
    public List<DailyCheckin> findAll() { return dailyCheckinRepository.findAll();}

    @Override
    public List<DailyCheckin> search(Date date) {
        return dailyCheckinRepository.search(date);
    }

}
