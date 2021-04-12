package edu.gyj.backend.mapper.college;

import edu.gyj.backend.entity.college.CollegeMonthCSEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollegeMonthCSMapperTest {
    @Autowired
    CollegeMonthCSMapper collegeMonthCSMapper;

    @Test
    void findAll() {
        List<CollegeMonthCSEntity> collegeMonthCSEntities = collegeMonthCSMapper.findAll();
        collegeMonthCSEntities.forEach(System.out::println);
    }

    @Test
    void findByCollegeId() {
        List<CollegeMonthCSEntity> collegeMonthCSEntities = collegeMonthCSMapper.findByCollegeId(1);
        collegeMonthCSEntities.forEach(System.out::println);
    }

    @Test
    void findByMonth() {
        List<CollegeMonthCSEntity> collegeMonthCSEntities = collegeMonthCSMapper.findByMonth(4,2021);
        collegeMonthCSEntities.forEach(System.out::println);
    }

    @Test
    void findByYear() {
        List<CollegeMonthCSEntity> collegeMonthCSEntities = collegeMonthCSMapper.findByYear(2021);
        collegeMonthCSEntities.forEach(System.out::println);
    }
}