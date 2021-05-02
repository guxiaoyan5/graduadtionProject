package edu.gyj.backend.mapper.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class StudentMonthTCSMapperTest {
    @Autowired
    StudentMonthTCSMapper studentMonthTCSMapper;
    @Test
    void findAll() {
        studentMonthTCSMapper.findAll().forEach(System.out::println);
    }

    @Test
    void findBySid() {
        studentMonthTCSMapper.findBySid("100001").forEach(System.out::println);
    }

    @Test
    void findByMonth() {
        studentMonthTCSMapper.findByMonth(4,2021).forEach(System.out::println);
    }

    @Test
    void findByYear() {
        studentMonthTCSMapper.findByYear(2021).forEach(System.out::println);
    }
}