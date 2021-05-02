package edu.gyj.backend.mapper.student;

import edu.gyj.backend.entity.student.StudentTCSEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class StudentTCSMapperTest {
    @Autowired
    StudentTCSMapper studentTCSMapper;
    @Test
    void findAll() {
        List<StudentTCSEntity> studentTCSEntities = studentTCSMapper.findAll();
        studentTCSEntities.forEach(System.out::println);
    }

    @Test
    void findBySid() {
        List<StudentTCSEntity> studentTCSEntities = studentTCSMapper.findBySid("100001");
        studentTCSEntities.forEach(System.out::println);
    }
}