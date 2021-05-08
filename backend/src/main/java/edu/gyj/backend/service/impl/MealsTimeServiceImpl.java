package edu.gyj.backend.service.impl;

import edu.gyj.backend.entity.MealsTime;
import edu.gyj.backend.mapper.MealsTimeMapper;
import edu.gyj.backend.service.MealsTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MealsTimeServiceImpl implements MealsTimeService {
    @Autowired
    MealsTimeMapper mealsTimeMapper;

    @Override
    public List<MealsTime> findAll() {
        return mealsTimeMapper.findAll();
    }
}
