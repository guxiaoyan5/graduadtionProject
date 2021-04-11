package edu.gyj.backend.mapper.student;

import edu.gyj.backend.entity.student.StudentMonthCSEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentMonthCSMapperTest {
    @Autowired
    StudentMonthCSMapper studentMonthCSMapper;
    @Test
    void findBySid() {
        List<StudentMonthCSEntity> studentMonthCSEntities = studentMonthCSMapper.findBySid("100001");
        studentMonthCSEntities.forEach(e->System.out.println(e));
    }

    @Test
    void findByName() {
        List<StudentMonthCSEntity> studentMonthCSEntities = studentMonthCSMapper.findByName("100001");
        studentMonthCSEntities.forEach(e->System.out.println(e));
    }

    @Test
    void findByMonth() {
        List<StudentMonthCSEntity> studentMonthCSEntities = studentMonthCSMapper.findByMonth(3,2021);
        studentMonthCSEntities.forEach(e->System.out.println(e));
    }

    @Test
    void findByYear() {
        List<StudentMonthCSEntity> studentMonthCSEntities = studentMonthCSMapper.findByYear(2021);
        studentMonthCSEntities.forEach(e->System.out.println(e));
    }
}