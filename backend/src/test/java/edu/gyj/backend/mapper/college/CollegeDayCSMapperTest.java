package edu.gyj.backend.mapper.college;

import edu.gyj.backend.entity.college.CollegeDayCSEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollegeDayCSMapperTest {
@Autowired
CollegeDayCSMapper collegeDayCSMapper;
    @Test
    void findAll() {
        List<CollegeDayCSEntity> collegeDayCSEntities = collegeDayCSMapper.findAll();
        collegeDayCSEntities.forEach(System.out::println);
    }

    @Test
    void findByCollegeId() {
        List<CollegeDayCSEntity> collegeDayCSEntities = collegeDayCSMapper.findByCollegeId(1);
        collegeDayCSEntities.forEach(System.out::println);
    }

    @Test
    void findByDate() {
        List<CollegeDayCSEntity> collegeDayCSEntities = collegeDayCSMapper.findByDate(Date.valueOf("2021-4-1"));
        collegeDayCSEntities.forEach(System.out::println);
    }
}