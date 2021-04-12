package edu.gyj.backend.mapper.college;

import edu.gyj.backend.entity.college.CollegeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollegeMapperTest {
    @Autowired
    CollegeMapper collegeMapper;

    @Test
    void findById() {
        CollegeEntity collegeEntity = collegeMapper.findById(1);
        System.out.println(collegeEntity);
    }

    @Test
    void findAll() {
        List<CollegeEntity> collegeEntities = collegeMapper.findAll();
        collegeEntities.forEach(System.out::println);
    }

    @Test
    void insertCollege() {
        System.out.println(collegeMapper.insertCollege(new CollegeEntity(0,"213")));
    }
    @Test
    void updateCollege() {
        System.out.println(collegeMapper.updateCollege(new CollegeEntity(5,"222")));
    }

    @Test
    void deleteCollege() {
        System.out.println(collegeMapper.deleteCollege(5));
    }
}