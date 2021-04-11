package edu.gyj.backend.mapper.student;

import edu.gyj.backend.entity.student.StudentCSEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class StudentCSMapperTest {
    @Autowired
    StudentCSMapper studentCSMapper;
    @Test
    void findAll() {
        List<StudentCSEntity> studentCSEntities = studentCSMapper.findAll();
        studentCSEntities.forEach(e->System.out.println(e));
    }

    @Test
    void findBySid() {
        List<StudentCSEntity> studentCSEntities = studentCSMapper.findBySid("100001");
        studentCSEntities.forEach(e->System.out.println(e));
    }

    @Test
    void findByName() {
        List<StudentCSEntity> studentCSEntities = studentCSMapper.findByName("100001");
        studentCSEntities.forEach(e->System.out.println(e));
    }
}