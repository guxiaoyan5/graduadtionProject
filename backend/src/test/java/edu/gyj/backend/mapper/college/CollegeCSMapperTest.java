package edu.gyj.backend.mapper.college;

import edu.gyj.backend.entity.college.CollegeCSEntity;
import edu.gyj.backend.entity.college.CollegeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollegeCSMapperTest {
    @Autowired
    CollegeCSMapper collegeCSMapper;

    @Test
    void findAll() {
        List<CollegeCSEntity> collegeEntities = collegeCSMapper.findAll();
        collegeEntities.forEach(System.out::println);
    }

    @Test
    void findByCollegeId() {
        List<CollegeCSEntity> collegeEntities = collegeCSMapper.findByCollegeId(1);
        collegeEntities.forEach(System.out::println);
    }
}