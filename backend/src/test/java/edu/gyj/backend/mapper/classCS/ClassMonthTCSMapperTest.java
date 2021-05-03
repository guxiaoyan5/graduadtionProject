package edu.gyj.backend.mapper.classCS;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClassMonthTCSMapperTest {
    @Autowired
    ClassMonthTCSMapper classMonthTCSMapper;

    @Test
    void findAll() {
        classMonthTCSMapper.findAll().forEach(System.out::println);
    }

    @Test
    void findByClassId() {
        classMonthTCSMapper.findByClassId(1).forEach(System.out::println);
    }

    @Test
    void findByMonth() {
        classMonthTCSMapper.findByMonth(4,2021).forEach(System.out::println);
    }

    @Test
    void findByYear() {
        classMonthTCSMapper.findByYear(2021).forEach(System.out::println);
    }

    @Test
    void findByClassIdAndYearAndMonth() {
        classMonthTCSMapper.findByClassIdAndYearAndMonth(1,4,2021).forEach(System.out::println);
    }

    @Test
    void findByClassIdAndYear() {
        classMonthTCSMapper.findByClassIdAndYear(1,2021).forEach(System.out::println);
    }
}