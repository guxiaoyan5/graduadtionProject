package edu.gyj.backend.service.impl;

import edu.gyj.backend.entity.analysis.AnalysisEntity;
import edu.gyj.backend.mapper.analysis.AnalysisMapper;
import edu.gyj.backend.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService {
    @Autowired
    AnalysisMapper analysisMapper;

    @Override
    public List<AnalysisEntity> findall() {
        return analysisMapper.findAll();
    }
}
