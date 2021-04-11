package edu.gyj.backend.mapper.student;

import edu.gyj.backend.entity.student.StudentDayCSEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class StudentDayCSMapperTest {
    @Autowired
    StudentDayCSMapper studentDayCSMapper;
    @Test
    void findAll() {
        List<StudentDayCSEntity>  studentDayCSEntities = studentDayCSMapper.findAll();
        studentDayCSEntities.forEach(e->System.out.println(e));
    }

    @Test
    void findBySid() {
        List<StudentDayCSEntity>  studentDayCSEntities = studentDayCSMapper.findBySid("100001");
        studentDayCSEntities.forEach(e->System.out.println(e));
    }

    @Test
    void findByName() {
        List<StudentDayCSEntity>  studentDayCSEntities = studentDayCSMapper.findByName("100001");
        studentDayCSEntities.forEach(e->System.out.println(e));
    }

    @Test
    void findByDate() {
        List<StudentDayCSEntity>  studentDayCSEntities = studentDayCSMapper.findByDate(Date.valueOf("2021-3-21"));
        studentDayCSEntities.forEach(e->System.out.println(e));
    }
}