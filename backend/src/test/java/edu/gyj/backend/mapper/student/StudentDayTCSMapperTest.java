package edu.gyj.backend.mapper.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentDayTCSMapperTest {
    @Autowired
    StudentDayTCSMapper studentDayTCSMapper;

    @Test
    void findAll() {
        studentDayTCSMapper.findAll().forEach(System.out::println);
    }

    @Test
    void findBySid() {
        studentDayTCSMapper.findBySid("100001").forEach(System.out::println);
    }

    @Test
    void findByDate() {
        studentDayTCSMapper.findByDate(Date.valueOf("2021-4-1")).forEach(System.out::println);
    }
}