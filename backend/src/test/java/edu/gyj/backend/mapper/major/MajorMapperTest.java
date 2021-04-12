package edu.gyj.backend.mapper.major;

import edu.gyj.backend.entity.major.MajorEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MajorMapperTest {
    @Autowired
    MajorMapper majorMapper;

    @Test
    void findById() {
        MajorEntity majorEntity = majorMapper.findById(1);
        System.out.println(majorEntity);
    }

    @Test
    void findAll() {
        List<MajorEntity> majorEntities = majorMapper.findAll();
        majorEntities.forEach(System.out::println);
    }

    @Test
    void findByCollegeId() {
        List<MajorEntity> majorEntities = majorMapper.findByCollegeId(1);
        majorEntities.forEach(System.out::println);
    }

    @Test
    void insertMajor() {
        majorMapper.insertMajor(new MajorEntity(-1, "1", 1));
    }

    @Test
    void updateMajor() {
        majorMapper.updateMajor(new MajorEntity(9, "1", 2));
    }

    @Test
    void deleteMajor() {
        majorMapper.deleteMajor(9);
    }
}