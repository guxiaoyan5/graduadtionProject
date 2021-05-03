package edu.gyj.backend.mapper.college;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollegeMonthTCSMapperTest {
    @Autowired
    CollegeMonthTCSMapper collegeMonthTCSMapper;

    @Test
    void findAll() {
        collegeMonthTCSMapper.findAll().forEach(System.out::println);
    }

    @Test
    void findByCollegeId() {
        collegeMonthTCSMapper.findByCollegeId(1).forEach(System.out::println);
    }

    @Test
    void findByMonth() {
        collegeMonthTCSMapper.findByMonth(4,2021).forEach(System.out::println);
    }

    @Test
    void findByYear() {
        collegeMonthTCSMapper.findByYear(2021).forEach(System.out::println);
    }

    @Test
    void findByCollegeIdAndYear() {
        collegeMonthTCSMapper.findByCollegeIdAndYear(1,2021).forEach(System.out::println);
    }

    @Test
    void findByCollegeIdAndYearAndMonth() {
        collegeMonthTCSMapper.findByCollegeIdAndYearAndMonth(1,2021,4).forEach(System.out::println);
    }
}