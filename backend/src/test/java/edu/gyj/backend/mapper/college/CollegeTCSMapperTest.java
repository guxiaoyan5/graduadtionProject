package edu.gyj.backend.mapper.college;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollegeTCSMapperTest {
    @Autowired
    CollegeTCSMapper collegeTCSMapper;

    @Test
    void findAll() {
        collegeTCSMapper.findAll().forEach(System.out::println);
    }

    @Test
    void findByCollegeId() {
        collegeTCSMapper.findByCollegeId(1).forEach(System.out::println);
    }
}