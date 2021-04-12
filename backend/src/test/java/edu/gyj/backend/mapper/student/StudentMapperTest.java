package edu.gyj.backend.mapper.student;

import edu.gyj.backend.entity.student.StudentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentMapperTest {
    @Autowired
    StudentMapper studentMapper;

    @Test
    void findAll() {
        List<StudentEntity> studentEntities = studentMapper.findAll();
        studentEntities.forEach(System.out::println);
    }

    @Test
    void findById() {
        StudentEntity studentEntities = studentMapper.findById("100001");
        System.out.println(studentEntities);
    }

    @Test
    void findByClassId() {
        List<StudentEntity> studentEntities = studentMapper.findByClassId(1);
        studentEntities.forEach(System.out::println);
    }

    @Test
    void findByMajorId() {
        List<StudentEntity> studentEntities = studentMapper.findByMajorId(1);
        studentEntities.forEach(System.out::println);
    }

    @Test
    void findByCollegeId() {
        List<StudentEntity> studentEntities = studentMapper.findByCollegeId(1);
        studentEntities.forEach(System.out::println);
    }

    @Test
    void insertStudent() {
        studentMapper.insertStudent(new StudentEntity("999999", "999999", 1, 1, 1, "男"));
    }

    @Test
    void updateStudent() {
        studentMapper.updateStudent(new StudentEntity("999999", "", -1, -1, -1, "女"));
    }

    @Test
    void deleteStudent() {
        studentMapper.deleteStudent("999999");
    }
}