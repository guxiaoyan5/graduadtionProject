package edu.gyj.backend.mapper.major;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MajorMonthTCSMapperTest {
    @Autowired
    MajorMonthTCSMapper majorMonthTCSMapper;

    @Test
    void findAll() {
        majorMonthTCSMapper.findAll().forEach(System.out::println);
    }

    @Test
    void findByMajorId() {
        majorMonthTCSMapper.findByMajorId(1).forEach(System.out::println);
    }

    @Test
    void findByMonth() {
        majorMonthTCSMapper.findByMonth(4,2021).forEach(System.out::println);
    }

    @Test
    void findByYear() {
        majorMonthTCSMapper.findByYear(2021).forEach(System.out::println);
    }

    @Test
    void findByMajorIdAndYearAndMonth() {
        majorMonthTCSMapper.findByMajorIdAndYearAndMonth(1,2021,4).forEach(System.out::println);
    }

    @Test
    void findByMajorIdAndYear() {
        majorMonthTCSMapper.findByMajorIdAndYear(1,2021).forEach(System.out::println);
    }
}