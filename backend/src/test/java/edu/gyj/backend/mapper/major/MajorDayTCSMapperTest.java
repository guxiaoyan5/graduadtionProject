package edu.gyj.backend.mapper.major;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MajorDayTCSMapperTest {
    @Autowired
    MajorDayTCSMapper majorDayTCSMapper;

    @Test
    void findAll() {
        majorDayTCSMapper.findAll().forEach(System.out::println);
    }

    @Test
    void findByMajorId() {
        majorDayTCSMapper.findByMajorId(1).forEach(System.out::println);
    }

    @Test
    void findByDate() {
        majorDayTCSMapper.findByDate(Date.valueOf("2021-4-1")).forEach(System.out::println);
    }

    @Test
    void findByDates() {
        majorDayTCSMapper.findByDates(1,Date.valueOf("2021-4-1"),Date.valueOf("2021-4-2")).forEach(System.out::println);
    }
}