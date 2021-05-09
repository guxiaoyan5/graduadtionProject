package edu.gyj.backend.mapper.analysis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnalysisMapperTest {
    @Autowired
    AnalysisMapper analysisMapper;

    @Test
    void findAll() {
        analysisMapper.findAll().forEach(System.out::println);
    }
}