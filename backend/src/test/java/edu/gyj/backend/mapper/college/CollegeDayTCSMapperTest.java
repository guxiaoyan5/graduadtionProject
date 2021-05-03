package edu.gyj.backend.mapper.college;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollegeDayTCSMapperTest {
    @Autowired
    CollegeDayTCSMapper collegeDayTCSMapper;

    @Test
    void findAll() {
        collegeDayTCSMapper.findAll().forEach(System.out::println);
    }

    @Test
    void findByCollegeId() {
        collegeDayTCSMapper.findByCollegeId(1).forEach(System.out::println);
    }

    @Test
    void findByDate() {
        collegeDayTCSMapper.findByDate(Date.valueOf("2021-4-1")).forEach(System.out::println);
    }

    @Test
    void findByDates() {
        collegeDayTCSMapper.findByDates(1,Date.valueOf("2021-4-1"),Date.valueOf("2021-4-2")).forEach(System.out::println);
    }
}